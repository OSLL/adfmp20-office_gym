package ru.adfmp.officegym.accelerometer

import org.junit.Assert.assertEquals
import org.junit.Test

class WaveStrategyTest {

    @Test
    fun checkWave() {
        val strategy = Wave(2.0, 1.0)
        val data = listOf(-2.0, -1.5, -1.0, 2.0, -2.0, -1.5)
        assertEquals(StrategyStatus.OK, strategy.check(data).first)
    }

    @Test
    fun testConst() {
        val strategy = Wave(2.0, 1.0)
        val data = List(10) { 2.0 }
        assertEquals(StrategyStatus.DO_FASTER, strategy.check(data).first)
    }

    @Test
    fun testSlow() {
        val strategy = Wave(1.0, 1.0)
        val data = listOf(-2.0, -1.5, 1.0, 2.0, 1.0, 0.0, -1.5, -2.0, -1.5, -0.5, 0.0, 1.0)
        assertEquals(StrategyStatus.DO_FASTER, strategy.check(data).first)
    }
}
