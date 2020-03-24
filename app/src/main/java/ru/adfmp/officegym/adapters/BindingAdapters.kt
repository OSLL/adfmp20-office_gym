package ru.adfmp.officegym.adapters

import android.text.format.DateUtils
import android.view.View
import android.widget.NumberPicker
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import ca.antonious.materialdaypicker.MaterialDayPicker
import ru.adfmp.officegym.R
import ru.adfmp.officegym.database.Exercise
import ru.adfmp.officegym.database.converters.Converters


@BindingAdapter("isGone")
fun bindIsGone(view: View, id: Long) {
    view.visibility = if (id == 0L) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("isVisible")
fun bindIsVisible(view: View, id: Long) {
    view.visibility = if (id != 0L) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("totalDuration")
fun bindTotalDuration(textView: TextView, exercises: List<Exercise>?) {
    val resources = textView.context.resources
    val text = resources.getString(
        R.string.total_duration,
        exercises?.sumBy { exercise -> exercise.duration } ?: 0
    )
    textView.text = text
}

@BindingAdapter("meanIntensity")
fun bindMeanIntensity(textView: TextView, exercises: List<Exercise>?) {
    val resources = textView.context.resources
    val text = resources.getString(
        R.string.mean_intensity,
        exercises?.sumBy { exercise -> exercise.intensity }?.div(if (exercises.size > 1) exercises.size else 1)
            ?: 0
    )
    textView.text = text
}

@BindingAdapter("baseTotalDuration")
fun bindBaseTotalDuration(textView: TextView, exercises: List<Exercise>?) {
    val resources = textView.context.resources
    val text = resources.getString(
        R.string.time,
        DateUtils.formatElapsedTime((exercises?.sumBy { exercise -> exercise.duration }
            ?: 0).toLong()).toString()
    )
    textView.text = text
}

@BindingAdapter("baseMeanIntensity")
fun bindBaseMeanIntensity(textView: TextView, exercises: List<Exercise>?) {
    val resources = textView.context.resources
    val text = resources.getString(
        R.string.data,
        exercises?.sumBy { exercise -> exercise.intensity }?.div(if (exercises.size > 1) exercises.size else 1)
            ?: 0
    )
    textView.text = text
}

@BindingAdapter("duration")
fun bindDuration(textView: TextView, duration: Long) {
    val resources = textView.context.resources
    val text = resources.getString(R.string.duration, duration)
    textView.text = text
}

@BindingAdapter("recommendedDuration")
fun bindRecommendedDuration(textView: TextView, recommendedDuration: Long) {
    val resources = textView.context.resources
    val text = resources.getString(R.string.recommended_duration, recommendedDuration)
    textView.text = text
}


@BindingAdapter("intensity")
fun bindIntensity(textView: TextView, intensity: Long) {
    val resources = textView.context.resources
    val text = resources.getString(R.string.intensity, intensity)
    textView.text = text
}

@BindingAdapter("pickRecommendedDuration")
fun bindPickRecommendedDuration(numberPicker: NumberPicker, recommendedDuration: Long) {
    numberPicker.value = recommendedDuration.toInt()
}

@InverseBindingAdapter(attribute = "pickRecommendedDuration")
fun getPickRecommendedDuration(numberPicker: NumberPicker): Int {
    return numberPicker.value
}

@BindingAdapter("pickRecommendedDurationAttrChanged")
fun pickRecommendedDurationAttrChangedListener(
    numberPicker: NumberPicker,
    attrChange: InverseBindingListener
) {
    numberPicker.setOnValueChangedListener { _, _, _ -> attrChange.onChange() }
}

@BindingAdapter("pickDuration")
fun setDuration(numberPicker: NumberPicker, duration: Long) {
    numberPicker.value = duration.toInt()
}

@BindingAdapter("pickDurationAttrChanged", requireAll = false)
fun pickDurationAttrChangedListener(
    numberPicker: NumberPicker,
    attrChange: InverseBindingListener
) {
    numberPicker.setOnValueChangedListener { _, _, _ -> attrChange.onChange() }
}

@InverseBindingAdapter(attribute = "pickDuration")
fun getPickDuration(numberPicker: NumberPicker): Int {
    return numberPicker.value
}

@BindingAdapter("pickIntensity")
fun bindPickIntensity(numberPicker: NumberPicker, intensity: Long) {
    numberPicker.value = intensity.toInt()
}

@InverseBindingAdapter(attribute = "pickIntensity")
fun getPickIntensity(numberPicker: NumberPicker): Int {
    return numberPicker.value
}

@BindingAdapter("pickIntensityAttrChanged")
fun pickIntensityAttrChangedListener(
    numberPicker: NumberPicker,
    attrChange: InverseBindingListener
) {
    numberPicker.setOnValueChangedListener { _, _, _ -> attrChange.onChange() }
}

@BindingAdapter("pickHour")
fun bindPickHour(numberPicker: NumberPicker, hour: Int) {
    numberPicker.value = hour
}

@InverseBindingAdapter(attribute = "pickHour")
fun getPickHour(numberPicker: NumberPicker): Int {
    return numberPicker.value
}

@BindingAdapter("pickHourAttrChanged")
fun pickHourAttrChangedListener(
    numberPicker: NumberPicker,
    attrChange: InverseBindingListener
) {
    numberPicker.setOnValueChangedListener { _, _, _ -> attrChange.onChange() }
}

@BindingAdapter("pickMinute")
fun bindPickMinute(numberPicker: NumberPicker, hour: Int) {
    numberPicker.value = hour
}

@InverseBindingAdapter(attribute = "pickMinute")
fun getPickMinute(numberPicker: NumberPicker): Int {
    return numberPicker.value
}

@BindingAdapter("pickMinuteAttrChanged")
fun pickDaysAttrChangedListener(
    numberPicker: NumberPicker,
    attrChange: InverseBindingListener
) {
    numberPicker.setOnValueChangedListener { _, _, _ -> attrChange.onChange() }
}

@BindingAdapter("pickDays")
fun bindPickDays(dayPicker: MaterialDayPicker, days: Map<Converters.DayOfWeek, Boolean>) {
    var selectedDays = mutableListOf<MaterialDayPicker.Weekday>()
    if (days[Converters.DayOfWeek.MONDAY]!!) {
        selectedDays.add(MaterialDayPicker.Weekday.MONDAY)
    }
    if (days[Converters.DayOfWeek.TUESDAY]!!) {
        selectedDays.add(MaterialDayPicker.Weekday.TUESDAY)
    }
    if (days[Converters.DayOfWeek.WEDNESDAY]!!) {
        selectedDays.add(MaterialDayPicker.Weekday.WEDNESDAY)
    }
    if (days[Converters.DayOfWeek.THURSDAY]!!) {
        selectedDays.add(MaterialDayPicker.Weekday.THURSDAY)
    }
    if (days[Converters.DayOfWeek.FRIDAY]!!) {
        selectedDays.add(MaterialDayPicker.Weekday.FRIDAY)
    }
    if (days[Converters.DayOfWeek.SATURDAY]!!) {
        selectedDays.add(MaterialDayPicker.Weekday.SATURDAY)
    }
    if (days[Converters.DayOfWeek.SUNDAY]!!) {
        selectedDays.add(MaterialDayPicker.Weekday.SUNDAY)
    }
    dayPicker.setSelectedDays(selectedDays)
}

@InverseBindingAdapter(attribute = "pickDays")
fun getPickDays(dayPicker: MaterialDayPicker): Map<Converters.DayOfWeek, Boolean> {
    var days = mutableMapOf<Converters.DayOfWeek, Boolean>()
    days[Converters.DayOfWeek.MONDAY] = dayPicker.isSelected(MaterialDayPicker.Weekday.MONDAY)
    days[Converters.DayOfWeek.TUESDAY] = dayPicker.isSelected(MaterialDayPicker.Weekday.TUESDAY)
    days[Converters.DayOfWeek.WEDNESDAY] = dayPicker.isSelected(MaterialDayPicker.Weekday.WEDNESDAY)
    days[Converters.DayOfWeek.THURSDAY] = dayPicker.isSelected(MaterialDayPicker.Weekday.THURSDAY)
    days[Converters.DayOfWeek.FRIDAY] = dayPicker.isSelected(MaterialDayPicker.Weekday.FRIDAY)
    days[Converters.DayOfWeek.SATURDAY] = dayPicker.isSelected(MaterialDayPicker.Weekday.SATURDAY)
    days[Converters.DayOfWeek.SUNDAY] = dayPicker.isSelected(MaterialDayPicker.Weekday.SUNDAY)
    return days
}


@BindingAdapter("pickDaysAttrChanged")
fun pickDaysAttrChangedListener(
    numberPicker: MaterialDayPicker,
    attrChange: InverseBindingListener
) {
    numberPicker.setDaySelectionChangedListener { list: List<MaterialDayPicker.Weekday> -> attrChange.onChange() }
}
