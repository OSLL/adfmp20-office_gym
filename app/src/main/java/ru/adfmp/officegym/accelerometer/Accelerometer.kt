package ru.adfmp.officegym.accelerometer

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager


class Accelerometer(
    application: Application,
    private val strategyX: IStrategy,
    private val strategyY: IStrategy,
    private val strategyZ: IStrategy,
    private val callback: (status: StrategyStatus) -> Unit
) : SensorEventListener {

    private val maxDataPoints = 200

    private var sensorManager: SensorManager =
        application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var accelerometer: Sensor

    private var seriesX: MutableList<Double> = mutableListOf()
    private var seriesY: MutableList<Double> = mutableListOf()
    private var seriesZ: MutableList<Double> = mutableListOf()

    var counter = 0

    init {
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    fun start() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
    }

    fun pause() {
        sensorManager.unregisterListener(this)
        clear()
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            update(event)
        }
    }

    private fun update(event: SensorEvent) {
        val values = event.values
        val x = values[0]
        val y = values[1]
        val z = values[2]

        seriesX.add(x.toDouble())
        seriesY.add(y.toDouble())
        seriesZ.add(z.toDouble())

        if (seriesX.size == maxDataPoints) {
            analyse()
            clear()
        }
    }

    private fun clear() {
        seriesX.clear()
        seriesY.clear()
        seriesZ.clear()
    }

    private fun analyse() {
        val result =
            listOf(strategyX.check(seriesX), strategyY.check(seriesY), strategyZ.check(seriesZ))
        val status = result.map { pair -> pair.first }
        counter += result.sumBy { pair -> pair.second }

        callback(status.sorted()[0])
    }
}
