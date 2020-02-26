package ru.adfmp.officegym.models

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BottomNavigationViewModel : ViewModel() {
    private val mutableBottomNavigationVisibility = MutableLiveData<Int>()
    val bottomNavigationVisibility: LiveData<Int>
        get() = mutableBottomNavigationVisibility

    init {
        showBottomNav()
    }

    fun showBottomNav() = mutableBottomNavigationVisibility.postValue(View.VISIBLE)
    fun hideBottomNav() = mutableBottomNavigationVisibility.postValue(View.GONE)
}
