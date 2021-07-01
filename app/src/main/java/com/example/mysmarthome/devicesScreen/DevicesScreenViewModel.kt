package com.example.mysmarthome.devicesScreen

import android.app.Application
import android.content.res.Resources
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mysmarthome.R
import com.example.mysmarthome.network.CategoriesTypes
import com.example.mysmarthome.network.HomeApi
import com.example.mysmarthome.network.LampApi
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.math.absoluteValue

class DevicesScreenViewModel(
    val arduinoId : Int,
    val resources : Resources,
    application: Application
):AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _eventNavigation = MutableLiveData<Boolean>()
    val eventNavigate:LiveData<Boolean>
    get() = _eventNavigation

    private val _devices = MutableLiveData<List<Devices>>()
    val devices:LiveData<List<Devices>>
    get() = _devices

    private val _categories = MutableLiveData<List<CategoriesTypes>>()
    val categories: LiveData<List<CategoriesTypes>>
        get() = _categories

    private val _listOfCategories = MutableLiveData<List<Int>>()
    val listOfCategories:LiveData<List<Int>>
        get() = _listOfCategories

    private var devicesNames = resources.getStringArray(R.array.devices_Names)
    private var devicesImages = resources.getStringArray(R.array.devices_Images)
    private var devicesid = resources.getIntArray(R.array.devices_Id)

    private var Images = arrayOf(resources.getDrawable(R.drawable.laamp),resources.getDrawable(R.drawable.condition),
        resources.getDrawable(R.drawable.televation),resources.getDrawable(R.drawable.dooor),
        resources.getDrawable(R.drawable.caamera))

    init {
        getDevices(arduinoId)

    }

    private fun getDevices(arduinoId: Int) {
        uiScope.launch {
            try{
                val listResult = LampApi.retrofitService.getCategories(arduinoId)
                _categories.value = listResult
                var list:List<Int> = emptyList()
                listResult.forEach { list+=it.device_type }
                list = list.distinct().toList()
                _listOfCategories.value = list
                var devicesList:List<Devices> = emptyList()
                list.forEach { it ->
                    devicesList+=Devices(devicesid[it-1],devicesNames[it-1],Images[it-1])
                }

                println("Image:"+devicesImages[1])
                _devices.value=devicesList

            }
            catch (e: Exception){
                println("Failure 123: "+ e.message)
                _listOfCategories.value = ArrayList()
            }
        }
    }

    fun onDeviceClicked(device: Devices){
        Toast.makeText(getApplication(),device.deviceName,Toast.LENGTH_SHORT).show()
    }


}