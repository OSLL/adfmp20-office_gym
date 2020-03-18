package ru.adfmp.officegym.accelerometer


import junit.framework.TestCase.assertEquals
import org.junit.Test

class MoveStrategyKtTest {

    @Test
    fun testMeanEmpty() {
        assertEquals(0.0, listOf<Double>().mean())
    }


    @Test
    fun testMean() {
        assertEquals(2.0, listOf(1.0, 2.0, 3.0).mean())
    }

    @Test
    fun diff() {
        assertEquals(listOf(1.0, 1.0), listOf(1.0, 2.0, 3.0).diff())
    }

    @Test
    fun emptyDiff() {
        assertEquals(emptyList<Double>(), listOf<Double>().diff())
    }
}
