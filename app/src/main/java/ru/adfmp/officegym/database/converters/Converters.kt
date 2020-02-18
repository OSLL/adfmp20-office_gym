package ru.adfmp.officegym.database.converters

import androidx.room.TypeConverter

class Converters {

    enum class DayOfWeek(val value: Int) {
        MONDAY(1),
        TUESDAY(2),
        WEDNESDAY(3),
        THURSDAY(4),
        FRIDAY(5),
        SATURDAY(6),
        SUNDAY(7);
    }

    @TypeConverter
    fun fromInt(value: Int): Map<DayOfWeek, Boolean> {
        return DayOfWeek.values().associate { it to ((value shl it.value) % 2 == 0) }
    }

    @TypeConverter
    fun dateToTimestamp(days: Map<DayOfWeek, Boolean>): Int {
        var value = 0
        days.forEach { (day, is_selected) -> value += if (is_selected) 1 shr day.value else 0 }
        return value
    }
}
