package ru.adfmp.officegym.database.converters

import androidx.room.TypeConverter
import com.google.gson.annotations.SerializedName


class Converters {

    enum class DayOfWeek(val value: Int) {
        MONDAY(0),
        TUESDAY(1),
        WEDNESDAY(2),
        THURSDAY(3),
        FRIDAY(4),
        SATURDAY(5),
        SUNDAY(6);
    }

    @TypeConverter
    fun fromInt(value: Int): Map<DayOfWeek, Boolean> {
        return DayOfWeek.values().associate { it to ((value shr it.value) % 2 == 0) }
    }

    @TypeConverter
    fun dateToTimestamp(days: Map<DayOfWeek, Boolean>): Int {
        var value = 0
        days.forEach { (day, is_selected) -> value += if (is_selected) 1 shl day.value else 0 }
        return value
    }

    enum class Strategy(val value: Int) {
        @SerializedName("zero")
        ZERO(0),
        @SerializedName("ignore")
        IGNORE(1),
        @SerializedName("constant")
        CONSTANT(2),
        @SerializedName("wave")
        WAVE(3);
    }

    @TypeConverter
    fun toStrategy(value: Int): Strategy = enumValues<Strategy>()[value]

    @TypeConverter
    fun fromStrategy(strategy: Strategy): Int = strategy.value
}
