package ru.adfmp.officegym.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import ru.adfmp.officegym.adapters.BaseAdapter
import ru.adfmp.officegym.adapters.callbacks.AlarmDiffCallback
import ru.adfmp.officegym.adapters.viewholders.AlarmViewHolder
import ru.adfmp.officegym.adapters.viewholders.createAlarmViewHolder
import ru.adfmp.officegym.database.Alarm
import ru.adfmp.officegym.databinding.FragmentAlarmBinding
import ru.adfmp.officegym.models.AlarmViewModel
import ru.adfmp.officegym.utils.InjectorUtils


class AlarmFragment : Fragment() {
    private val viewModel: AlarmViewModel by viewModels {
        InjectorUtils.provideAlarmViewModelFactory(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAlarmBinding.inflate(inflater, container, false)
        context ?: return binding.root
        val adapter = getAlarmAdapter()
        binding.alarmList.adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun getAlarmAdapter() =
        BaseAdapter(
            { p -> createAlarmViewHolder(p) },
            AlarmDiffCallback()
        )

    private fun subscribeUi(adapter: BaseAdapter<Alarm, AlarmViewHolder>) {
        viewModel.alarms.observe(viewLifecycleOwner, Observer { alarms -> adapter.submitList(alarms) })
    }
}
