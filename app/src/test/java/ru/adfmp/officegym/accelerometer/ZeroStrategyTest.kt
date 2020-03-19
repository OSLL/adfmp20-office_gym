package ru.adfmp.officegym.accelerometer

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ZeroStrategyTest {

    private lateinit var strategy: Zero

    @Before
    fun setUp() {
        strategy = Zero()
    }

    @Test
    fun checkZero() {
        val data = listOf(1.0, 2.0, 0.0, 1.5, -1.5)
        assertEquals(StrategyStatus.OK, strategy.check(data).first)
    }

    @Test
    fun checkNonZero() {
        val data = listOf(1.0, 2.0, 0.0, 1.5, -1.5, 15.0)
        assertEquals(StrategyStatus.STAY_STILL, strategy.check(data).first)
    }
}
