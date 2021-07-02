package com.example.mysmarthome.addDevices

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mysmarthome.network.LampApi
import com.example.mysmarthome.network.devicePins
import kotlinx.coroutines.*
import java.lang.Exception

class AddDevicesViewModel(
    val arduinoId : Int,
    val resources : Resources,
    application: Application
): AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _pinsForDevice = MutableLiveData<List<Int>>()
    val pinsForDevice : LiveData<List<Int>>
    get() = _pinsForDevice

    fun getDevicePins(device:String, arduinoId: Int, pins:List<Int>){
        uiScope.launch {
            try {
                val pinsList = LampApi.retrofitService.getDevicePins(device,arduinoId)
                var list:List<Int> = emptyList()
                pinsList.forEach { list+=it.pin }
                println("All Pins are:" + pins)
                println("UsedPins are:" + list)
                _pinsForDevice.value = pins.minus(list)
                println("AvailiblePins are:" + _pinsForDevice.value)

            }
            catch (e:Exception){
                println("Failure: " + e)
                _pinsForDevice.value=pins
            }
        }
    }

    fun addDevicesInTables(deviceType:String, deviceName:String, devicePin:Int, arduinoId: Int){
        uiScope.launch {
            addDevice(deviceType,deviceName,devicePin,arduinoId)
        }

    }

    private suspend fun addDevice(deviceType:String, deviceName:String, devicePin:Int, arduinoId: Int){
        withContext(Dispatchers.IO) {
        when(deviceType){
            "lamp" -> LampApi.retrofitService.addLamp(deviceName,devicePin,arduinoId)
            "ac" -> LampApi.retrofitService.addAc(deviceName,devicePin,arduinoId)
            "tv" -> LampApi.retrofitService.addTv(deviceName,devicePin,arduinoId)
            else -> print("No Devices")
        }
        }
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}