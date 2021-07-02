package com.example.mysmarthome.onOffDevices

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mysmarthome.devicesScreen.DevicesScreenViewModel

class OnOffDevicesViewModelFactory (
    private val arduinoId: Int,
    private val deviceType: Int,
    private val resources : Resources,
    private val application: Application
) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OnOffDevicesViewModel::class.java)) {
            return OnOffDevicesViewModel(arduinoId,deviceType,resources, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}