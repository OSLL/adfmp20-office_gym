package ru.adfmp.officegym.adapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.adfmp.officegym.R
import ru.adfmp.officegym.database.Exercise

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("totalDuration")
fun bindTotalDuration(textView: TextView, exercises: List<Exercise>) {
    val resources = textView.context.resources
    val text = resources.getString(
        R.string.total_duration,
        exercises.sumBy { exercise -> exercise.duration })
    textView.text = text
}

@BindingAdapter("duration")
fun bindDuration(textView: TextView, duration: Long) {
    val resources = textView.context.resources
    val text = resources.getString(R.string.duration, duration)
    textView.text = text
}

@BindingAdapter("intensity")
fun bindIntensity(textView: TextView, intensity: Long) {
    val resources = textView.context.resources
    val text = resources.getString(R.string.intensity, intensity)
    textView.text = text
}
