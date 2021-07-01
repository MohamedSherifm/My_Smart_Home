package com.example.mysmarthome.devicesScreen

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DevicesScreenViewModelFactory (
    private val arduinoId: Int,
    private val resources : Resources,
    private val application: Application
) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DevicesScreenViewModel::class.java)) {
            return DevicesScreenViewModel(arduinoId,resources, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}