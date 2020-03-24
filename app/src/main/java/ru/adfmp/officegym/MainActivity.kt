package ru.adfmp.officegym

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.adfmp.officegym.databinding.ActivityMainBinding
import ru.adfmp.officegym.models.BottomNavigationViewModel
import ru.adfmp.officegym.ui.HomeFragmentDirections

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bottomNavigationModel: BottomNavigationViewModel by viewModels()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            lifecycleOwner = this@MainActivity
            bottomNavigationViewModel = bottomNavigationModel
        }

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        val fullScreenDestinations = setOf(R.id.navigation_run_workout_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                in fullScreenDestinations -> bottomNavigationModel.hideBottomNav()
                else -> bottomNavigationModel.showBottomNav()
            }
        }

        tryStartFromAlarm()
    }

    private fun tryStartFromAlarm() {
        val intent = intent ?: return
        val workoutId = intent.getLongExtra("workout_id", -1)
        if (workoutId == -1L) return
        val direction = HomeFragmentDirections.actionNavHomeToNavStartWorkoutFragment(workoutId)
        findNavController(R.id.nav_host_fragment).navigate(direction)
    }
}
