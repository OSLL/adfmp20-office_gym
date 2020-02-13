package ru.adfmp.officegym.database.converters

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromInt(value: Int): Array<Boolean> {
        return Array(7) { i -> (value shl i) % 2 == 0 }
    }

    @TypeConverter
    fun dateToTimestamp(days: Array<Boolean>): Int {
        var value = 0
        for ((i, day) in days.withIndex()) {
            value += if (day) 1 shr i else 0
        }
        return value
    }
}
