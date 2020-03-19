package ru.adfmp.officegym.accelerometer

import ru.adfmp.officegym.database.converters.Converters
import kotlin.math.abs

enum class StrategyStatus(val message: String) {
    DO_FASTER("Try to do exercise faster!"),
    MORE_ACTIVE("Try to do exercise more active!"),
    STAY_STILL("Try not to move!"),
    OK("Very good!")
}

class StrategyFactory {
    companion object {
        fun create(strategy: Converters.Strategy, phase: Double, amplitude: Double) =
            when (strategy) {
                Converters.Strategy.ZERO -> Zero()
                Converters.Strategy.IGNORE -> Ignore()
                Converters.Strategy.CONSTANT -> Constant()
                Converters.Strategy.WAVE -> Wave(phase, amplitude)
            }
    }
}

fun List<Double>.mean(): Double {
    if (this.isEmpty()) {
        return 0.0
    }
    return this.sum() / this.size
}

fun List<Double>.diff(): List<Double> {
    val diff = mutableListOf<Double>()
    for (i in 1 until this.size) {
        if (i != 0) {
            diff.add(abs(this[i] - this[i - 1]))
        }
    }
    return diff
}

interface IStrategy {
    fun check(data: List<Double>): Pair<StrategyStatus, Int>
}

class Ignore : IStrategy {
    override fun check(data: List<Double>) = Pair(StrategyStatus.OK, 0)
}

class Zero : IStrategy {

    override fun check(data: List<Double>) =
        if (data.mean() < EPS) Pair(StrategyStatus.OK, 0) else Pair(StrategyStatus.STAY_STILL, 0)

    companion object {
        private const val EPS = 2
    }
}

class Constant : IStrategy {

    override fun check(data: List<Double>) =
        if (data.diff().mean() < EPS) Pair(
            StrategyStatus.OK,
            0
        ) else Pair(StrategyStatus.STAY_STILL, 0)

    companion object {
        private const val EPS = 2
    }
}

class Wave(private val maxPeriod: Double, private val minAmplitude: Double) : IStrategy {

    override fun check(data: List<Double>): Pair<StrategyStatus, Int> {
        var periods = mutableListOf<Double>()
        var i = 1
        while (i < data.size) {
            val start = i
            while (i < data.size && data[i] <= data[i - 1]) {
                i++
            }
            if (start == i) {
                while (i < data.size && data[i] >= data[i - 1]) {
                    i++
                }
            }
            periods.add((i - start).toDouble())
        }
        if (periods.size == 1) {
            return Pair(StrategyStatus.DO_FASTER, 0)
        }
        periods = periods.subList(1, periods.size - 1)
        return when {
            periods.mean() > maxPeriod -> {
                return Pair(StrategyStatus.DO_FASTER, periods.size / 4)
            }
            data.isNotEmpty() && (data.max()!! - data.mean() < minAmplitude ||
                    data.min()!! - data.mean() > -minAmplitude) -> {
                return Pair(StrategyStatus.MORE_ACTIVE, periods.size / 4)
            }
            (periods.diff().mean()) < EPS -> {
                Pair(StrategyStatus.OK, periods.size / 4)
            }
            else -> Pair(StrategyStatus.STAY_STILL, periods.size / 4)
        }
    }

    companion object {
        private const val EPS = 10
    }
}
