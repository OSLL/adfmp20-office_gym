package ru.adfmp.officegym.adapters

import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import ru.adfmp.officegym.R
import ru.adfmp.officegym.database.Exercise


@BindingAdapter("isGone")
fun bindIsGone(view: View, id: Long) {
    view.visibility = if (id == 0L) {
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
        DateUtils.formatElapsedTime((exercises?.sumBy { exercise -> exercise.duration } ?: 0).toLong()).toString()
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

@BindingAdapter("editRecommendedDuration")
fun bindEditRecommendedDuration(editText: EditText, recommendedDuration: Long) {
    editText.setText(recommendedDuration.toString())
}

@InverseBindingAdapter(attribute = "app:editRecommendedDuration")
fun getEditRecommendedDuration(editText: EditText): Int {
    var duration = 0
    try {
        duration = editText.text.toString().toInt()
    } catch (nfe: NumberFormatException) {
        Toast.makeText(editText.context, "Duration should be number!", Toast.LENGTH_SHORT).show()
    }
    return duration
}


@BindingAdapter("editRecommendedDurationAttrChanged")
fun editRecommendedDurationAttrChangedListener(
    editText: EditText,
    attrChange: InverseBindingListener
) {
    editText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            attrChange.onChange()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            attrChange.onChange()
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            attrChange.onChange()
        }
    })
}

@BindingAdapter("editIntensity")
fun bindEditIntensity(editText: EditText, intensity: Long) {
    editText.setText(intensity.toString())
}

@InverseBindingAdapter(attribute = "editIntensity")
fun getEditIntensity(editText: EditText): Int {
    var intensity = 0
    try {
        intensity = editText.text.toString().toInt()
    } catch (nfe: NumberFormatException) {
        Toast.makeText(editText.context, "Intensity should be number!", Toast.LENGTH_SHORT).show()
    }
    return intensity
}

@BindingAdapter("editIntensityAttrChanged")
fun editIntensityAttrChangedListener(editText: EditText, attrChange: InverseBindingListener) {
    editText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            attrChange.onChange()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            attrChange.onChange()
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            attrChange.onChange()
        }
    })
}

@BindingAdapter("editDuration")
fun editDuration(editText: EditText, duration: Long) {
    editText.setText(duration.toString())
}

@InverseBindingAdapter(attribute = "editDuration")
fun getEditDuration(editText: EditText): Int {
    var duration = 0
    try {
        duration = editText.text.toString().toInt()
    } catch (nfe: NumberFormatException) {
        Toast.makeText(editText.context, "Duration should be number!", Toast.LENGTH_SHORT).show()
    }
    return duration
}

@BindingAdapter("editDurationAttrChanged")
fun editDurationAttrChangedListener(editText: EditText, attrChange: InverseBindingListener) {
    editText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            attrChange.onChange()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            attrChange.onChange()
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            attrChange.onChange()
        }
    })
}