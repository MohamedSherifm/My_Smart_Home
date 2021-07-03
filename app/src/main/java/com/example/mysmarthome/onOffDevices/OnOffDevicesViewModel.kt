package com.example.mysmarthome.onOffDevices

import android.app.Application
import android.content.res.Resources
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mysmarthome.OnOffDevices
import com.example.mysmarthome.R
import com.example.mysmarthome.network.AcProperty
import com.example.mysmarthome.network.LampApi
import com.example.mysmarthome.network.LampProperty
import com.example.mysmarthome.network.TvProperty
import kotlinx.coroutines.*
import java.lang.Exception

class OnOffDevicesViewModel(
    val arduinoId : Int,
    val deviceType: Int,
    val resources : Resources,
    application: Application
): AndroidViewModel(application) {

    private var viewModelJob= Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _devicesList = MutableLiveData<List<OnOffDevices>>()
    val devicesList : LiveData<List<OnOffDevices>>
        get() = _devicesList

    init{
        print("Hello")
        getDeviceList(deviceType)

    }

    private  fun getDeviceList(deviceType: Int) {
        uiScope.launch {
        try{

        when(deviceType){
            1 -> { val listResult = LampApi.retrofitService.getLampProperties(arduinoId)
                   var list:List<OnOffDevices> = emptyList()
                    listResult.forEach { list+= OnOffDevices(it.lamp_id,it.lamp_name,it.pin,resources.getDrawable(R.drawable.laamp),it.device_type) }
            _devicesList.value = list
            }

            2   -> {val listResult = LampApi.retrofitService.getAcProperties(arduinoId)
                var list:List<OnOffDevices> = emptyList()
                listResult.forEach { list+= OnOffDevices(it.ac_id,it.ac_name,it.pin,resources.getDrawable(R.drawable.condition),it.device_type,temp = it.ac_temp) }
                _devicesList.value = list}
            3 -> {val listResult =  LampApi.retrofitService.getTvProperties(arduinoId)
                var list:List<OnOffDevices> = emptyList()
                listResult.forEach { list+= OnOffDevices(it.tv_id,it.tv_name,it.pin,resources.getDrawable(R.drawable.televation),it.device_type,channel = it.channel) }
                _devicesList.value = list}

        }} catch (e:Exception){print("Exception:"+e)}}

    }


    fun turnOn(deviceId:Int, devicePin:Int, deviceType: Int ){
        uiScope.launch {
            println("Using this On")
            turnOnDevice(deviceId, devicePin, deviceType)

        }
    }

    fun turnOff(deviceId:Int, devicePin:Int, deviceType: Int ){
        uiScope.launch {
            turnOffDevice(deviceId, devicePin,  deviceType)
        }
    }

    private suspend fun turnOnDevice(deviceId: Int, devicePin: Int, deviceType: Int ) {
        withContext(Dispatchers.IO){
            when(deviceType){
                1 -> LampApi.retrofitService.updateLampStatus(deviceId, devicePin, arduinoId,1)
                2 -> LampApi.retrofitService.updateAcStatus(deviceId, devicePin, arduinoId,1)
                3 -> LampApi.retrofitService.updateTvStatus(deviceId, devicePin, arduinoId,1)
            }
        }
    }

    private suspend fun turnOffDevice(deviceId: Int, devicePin: Int, deviceType: Int ) {
        withContext(Dispatchers.IO){
            when(deviceType){
                1 -> LampApi.retrofitService.updateLampStatus(deviceId, devicePin, arduinoId,0)
                2 -> LampApi.retrofitService.updateAcStatus(deviceId, devicePin, arduinoId,0)
                3 -> LampApi.retrofitService.updateTvStatus(deviceId, devicePin, arduinoId,0)
            }
        }
    }

    fun upDevice(deviceId: Int, devicePin: Int, deviceType: Int){
        uiScope.launch {
            print("Using this On")
            upDeviceControl(deviceId, devicePin, deviceType)
        }
    }

    private suspend fun upDeviceControl(deviceId: Int, devicePin: Int, deviceType: Int) {
        withContext(Dispatchers.IO){
            when(deviceType){
                2 -> LampApi.retrofitService.updateAcTemp(deviceId,devicePin,arduinoId,1)
                3 -> LampApi.retrofitService.updateTvChannel(deviceId, devicePin, arduinoId,1)
            }
        }
    }

    fun downDevice(deviceId: Int, devicePin: Int, deviceType: Int){
        uiScope.launch {
            downDeviceControl(deviceId, devicePin, deviceType)
        }
    }

    private suspend fun downDeviceControl(deviceId: Int, devicePin: Int, deviceType: Int) {
        withContext(Dispatchers.IO){
            when(deviceType){
                2 -> LampApi.retrofitService.updateAcTemp(deviceId,devicePin,arduinoId,2)
                3 -> LampApi.retrofitService.updateTvChannel(deviceId, devicePin, arduinoId,2)
            }
        }
    }

    fun delete(tableName:String , deviceId: Int , deviceType: Int){
            uiScope.launch {
                deleteDevice(tableName, deviceId , deviceType)
                getDeviceList(deviceType)
            }
    }

    private suspend fun deleteDevice(tableName: String, deviceId: Int ,deviceType: Int) {
            withContext(Dispatchers.IO){
                when(deviceType){
                    1 -> LampApi.retrofitService.deleteLamp(tableName,deviceId)
                    2 -> LampApi.retrofitService.deleteAc(tableName,deviceId)
                    3 -> LampApi.retrofitService.deleteTv(tableName,deviceId)
                }

            }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}