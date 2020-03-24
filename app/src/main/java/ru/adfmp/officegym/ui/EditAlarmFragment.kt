package ru.adfmp.officegym.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ca.antonious.materialdaypicker.MaterialDayPicker
import ru.adfmp.officegym.database.converters.Converters
import ru.adfmp.officegym.databinding.FragmentEditAlarmBinding
import ru.adfmp.officegym.models.EditAlarmViewModel
import ru.adfmp.officegym.utils.InjectorUtils

class EditAlarmFragment : Fragment() {

    private val args: EditAlarmFragmentArgs by navArgs()

    private val viewModel: EditAlarmViewModel by activityViewModels {
        InjectorUtils.provideEditAlarmViewModelFactory(this, args.alarmId, args.workoutId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditAlarmBinding.inflate(inflater, container, false)
        context ?: return binding.root

        subscribeUi(binding)
        return binding.root
    }

    private fun subscribeUi(binding: FragmentEditAlarmBinding) {
        viewModel.baseAlarm.observe(viewLifecycleOwner) { alarm ->
            binding.alarm = alarm
            var days = alarm!!.days
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
                binding.dayPicker.setSelectedDays(selectedDays)
        }
        binding.workout = viewModel.workout
        binding.save.setOnClickListener {
            if (viewModel.insertOrUpdateAlarm(getPickDays(binding))) {
                exit()
            } else {
                if (viewModel.workout == null) {
                    Toast.makeText(
                        context, "Add workout to alarm.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context, "Exercise with such name has already exists.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        binding.hourPicker.minValue = 0
        binding.hourPicker.maxValue = 24
        binding.minutePicker.minValue = 0
        binding.minutePicker.maxValue = 60
        binding.cancel.setOnClickListener {
            exit()
        }
        binding.workoutLayout.setOnClickListener {
            val direction = EditAlarmFragmentDirections
                .actionEditAlarmToAddWorkoutToAlarmFragment()
            it.findNavController().navigate(direction)
        }

        binding.addWorkoutButton.setOnClickListener {
            val direction = EditAlarmFragmentDirections
                .actionEditAlarmToAddWorkoutToAlarmFragment()
            it.findNavController().navigate(direction)
        }
    }

    fun getPickDays(binding: FragmentEditAlarmBinding): Map<Converters.DayOfWeek, Boolean> {
        var days = mutableMapOf<Converters.DayOfWeek, Boolean>()
        var dayPicker = binding.dayPicker
        days[Converters.DayOfWeek.MONDAY] = dayPicker.isSelected(MaterialDayPicker.Weekday.MONDAY)
        days[Converters.DayOfWeek.TUESDAY] = dayPicker.isSelected(MaterialDayPicker.Weekday.TUESDAY)
        days[Converters.DayOfWeek.WEDNESDAY] = dayPicker.isSelected(MaterialDayPicker.Weekday.WEDNESDAY)
        days[Converters.DayOfWeek.THURSDAY] = dayPicker.isSelected(MaterialDayPicker.Weekday.THURSDAY)
        days[Converters.DayOfWeek.FRIDAY] = dayPicker.isSelected(MaterialDayPicker.Weekday.FRIDAY)
        days[Converters.DayOfWeek.SATURDAY] = dayPicker.isSelected(MaterialDayPicker.Weekday.SATURDAY)
        days[Converters.DayOfWeek.SUNDAY] = dayPicker.isSelected(MaterialDayPicker.Weekday.SUNDAY)
        return days
    }

    private fun exit() {
        activity!!.viewModelStore.clear()
        findNavController().popBackStack()
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard(activity!!.window.decorView.rootView)
    }

    private fun hideKeyboard(view: View) =
        getInputMethodManager().hideSoftInputFromWindow(view.windowToken, 0)

    private fun getInputMethodManager() =
        context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
}
