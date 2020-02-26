package ru.adfmp.officegym.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.adfmp.officegym.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.editExercisesImage.setOnClickListener {
            val direction =
                ProfileFragmentDirections.actionNavProfileToNavEditAllExercisesFragment()
            findNavController().navigate(direction)
        }
        binding.editWorkoutsImage.setOnClickListener {
            val direction = ProfileFragmentDirections.actionNavProfileToNavEditAllWorkoutsFragment()
            findNavController().navigate(direction)
        }
        setHasOptionsMenu(true)
        return binding.root
    }
}
