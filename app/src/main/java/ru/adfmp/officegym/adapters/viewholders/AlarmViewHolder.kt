package ru.adfmp.officegym.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import ru.adfmp.officegym.database.Alarm
import ru.adfmp.officegym.databinding.ListItemAlarmBinding
import ru.adfmp.officegym.ui.AlarmFragmentDirections

class AlarmViewHolder(
    private val binding: ListItemAlarmBinding
) : BaseViewHolder<Alarm>(binding) {

    override fun bind(item: Alarm) {
        binding.apply {
            alarm = item
            root.setOnClickListener {
                val direction = AlarmFragmentDirections
                    .actionNavAlarmToEditAlarmFragment(item.id, item.workoutId)
                it.findNavController().navigate(direction)
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
