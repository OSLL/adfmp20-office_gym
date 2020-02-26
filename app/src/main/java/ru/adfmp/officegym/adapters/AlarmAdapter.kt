package ru.adfmp.officegym.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.adfmp.officegym.database.Alarm
import ru.adfmp.officegym.databinding.ListItemAlarmBinding

class AlarmAdapter :
    ListAdapter<Alarm, AlarmAdapter.AlarmViewHolder>(AlarmDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AlarmViewHolder(
        ListItemAlarmBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = getItem(position)
        holder.bind(alarm)
    }

    class AlarmViewHolder(
        private val binding: ListItemAlarmBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Alarm) {
            binding.apply {
                alarm = item
                root.setOnClickListener {
                    //TODO: add navigation
                }
                executePendingBindings()
            }
        }
    }
}

private class AlarmDiffCallback : DiffUtil.ItemCallback<Alarm>() {

    override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm) = oldItem == newItem
}
