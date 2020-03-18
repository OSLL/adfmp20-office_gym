package ru.adfmp.officegym.accelerometer

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

class IgnoreStrategyTest {

    @Test
    fun check() {
        val random = Random(42)
        val data = List(100) { random.nextDouble(20.0) - 10 }
        assertEquals(StrategyStatus.OK, Ignore().check(data).first)
    }
}
