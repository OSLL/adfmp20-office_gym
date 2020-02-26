package ru.adfmp.officegym.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import ru.adfmp.officegym.adapters.BaseAdapter
import ru.adfmp.officegym.adapters.callbacks.BaseExerciseDiffCallback
import ru.adfmp.officegym.adapters.viewholders.EditBaseExerciseViewHolder
import ru.adfmp.officegym.adapters.viewholders.createBaseExerciseViewHolder
import ru.adfmp.officegym.database.BaseExercise
import ru.adfmp.officegym.databinding.FragmentEditAllExercisesBinding
import ru.adfmp.officegym.models.BaseExercisesViewModel
import ru.adfmp.officegym.utils.InjectorUtils

class EditAllExercisesFragment : Fragment() {

    private val viewModel: BaseExercisesViewModel by viewModels {
        InjectorUtils.provideBaseExercisesViewModelFactory(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditAllExercisesBinding.inflate(inflater, container, false)
        context ?: return binding.root
        val adapter = getBaseExerciseAdapter()
        binding.editExercisesList.adapter = adapter
        subscribeUi(binding, adapter)

        setHasOptionsMenu(true)
        return binding.root
    }


    private fun getBaseExerciseAdapter() =
        BaseAdapter({ p -> createBaseExerciseViewHolder(p) }, BaseExerciseDiffCallback())


    private fun subscribeUi(
        binding: FragmentEditAllExercisesBinding,
        adapter: BaseAdapter<BaseExercise, EditBaseExerciseViewHolder>
    ) {
        viewModel.baseExercises.observe(viewLifecycleOwner) { exercise ->
            adapter.submitList(exercise)
        }
        binding.addExerciseButton.setOnClickListener {
            val direction = EditAllExercisesFragmentDirections
                .actionNavEditAllExercisesToNavEditExerciseFragment()
            it.findNavController().navigate(direction)
        }
    }
}
