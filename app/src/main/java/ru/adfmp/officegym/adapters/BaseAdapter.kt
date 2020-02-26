package ru.adfmp.officegym.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.adfmp.officegym.adapters.viewholders.BaseViewHolder


class BaseAdapter<T, EntityViewHolder : BaseViewHolder<T>>(
    private val createHolder: (ViewGroup) -> EntityViewHolder,
    callback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, EntityViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        return createHolder(parent)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}
