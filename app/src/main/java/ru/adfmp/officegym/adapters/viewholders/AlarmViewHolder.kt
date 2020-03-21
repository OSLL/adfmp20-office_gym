package ru.adfmp.officegym.adapters.viewholders

import ru.adfmp.officegym.database.Alarm
import ru.adfmp.officegym.databinding.ListItemAlarmBinding
import android.view.ViewGroup
import android.view.LayoutInflater

class AlarmViewHolder(
    private val binding: ListItemAlarmBinding
) : BaseViewHolder<Alarm>(binding) {

    override fun bind(item: Alarm) {
        binding.apply {
            alarm = item
            root.setOnClickListener {
                //TODO: add navigation
            }
            executePendingBindings()
        }
    }
}

fun createAlarmViewHolder(parent: ViewGroup) = AlarmViewHolder(
    ListItemAlarmBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )
)
