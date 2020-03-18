package ru.adfmp.officegym.accelerometer

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ConstantStrategyTest {
    lateinit var strategy: Constant

    @Before
    fun setUp() {
        strategy = Constant()
    }

    @Test
    fun checkConst() {
        val data = listOf(1.0, 2.0, 1.0, 1.3, 2.3, 1.8, 0.8, 0.0)
        Assert.assertEquals(StrategyStatus.OK, strategy.check(data).first)
    }

    @Test
    fun checkNonConst() {
        val data = listOf(1.0, 2.0, 1.0, 1.3, 2.3, 1.8, 0.8, 0.0)
        Assert.assertEquals(StrategyStatus.OK, strategy.check(data).first)
    }
}
