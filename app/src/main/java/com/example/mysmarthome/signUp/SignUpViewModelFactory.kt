package com.example.mysmarthome.signUp

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mysmarthome.onOffDevices.OnOffDevicesViewModel

class SignUpViewModelFactory (
    private val application: Application
) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}