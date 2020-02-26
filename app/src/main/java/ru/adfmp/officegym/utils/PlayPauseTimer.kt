package ru.adfmp.officegym.utils

import android.os.CountDownTimer

class PlayPauseTimer(
    private val millisInFuture: Long,
    private val millisInterval: Long = 100,
    private val onFinish: () -> Unit,
    private val onTick: (millisUntilFinished: Long, percent: Double) -> Unit
) {
    private var timer = MyTimer(millisInFuture)

    fun start() {
        timer.start()
    }

    fun cancel() {
        timer.cancel()
    }

    fun pause() {
        timer.canceled = true
    }

    fun play() {
        timer.start()
    }

    private inner class MyTimer(
        millisInFuture: Long
    ) : CountDownTimer(millisInFuture, millisInterval) {
        var canceled: Boolean = false

        override fun onFinish() {
            this@PlayPauseTimer.onFinish()
        }

        override fun onTick(millisUntilFinished: Long) {
            if (canceled) {
                onCanceled(millisUntilFinished)
            }
            val progress = PERCENT_RATE * (1 - millisUntilFinished.toDouble() / millisInFuture)
            onTick(millisUntilFinished, progress)
        }

        private fun onCanceled(millisUntilFinished: Long) {
            timer = MyTimer(millisUntilFinished)
            cancel()
        }
    }

    companion object {
        const val MILLIS_IN_SECOND = 1000L
        private const val PERCENT_RATE = 100
    }
}
