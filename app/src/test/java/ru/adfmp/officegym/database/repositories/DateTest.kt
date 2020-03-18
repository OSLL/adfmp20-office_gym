package ru.adfmp.officegym.database.repositories


import org.junit.Assert
import org.junit.Test

class DateTest {
    private val date = 0L

    @Test
    fun testDateToString() {
        Assert.assertEquals("Jan-01", StatisticsRepository.dateToString(date))
    }

    @Test
    fun testDateParts() {
        Assert.assertEquals(listOf(1970, 0, 1), StatisticsRepository.dateToYearMonthDay(date))
    }
}