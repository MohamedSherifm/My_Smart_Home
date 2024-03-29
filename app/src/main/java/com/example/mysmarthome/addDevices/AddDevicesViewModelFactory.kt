package com.example.mysmarthome.addDevices

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mysmarthome.devicesScreen.DevicesScreenViewModel

class AddDevicesViewModelFactory (
    private val arduinoId: Int,
    private val resources : Resources,
    private val application: Application
) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddDevicesViewModel::class.java)) {
            return AddDevicesViewModel(arduinoId,resources, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}