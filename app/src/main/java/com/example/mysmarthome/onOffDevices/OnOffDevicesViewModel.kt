package com.example.mysmarthome.onOffDevices

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mysmarthome.OnOffDevices
import com.example.mysmarthome.R
import com.example.mysmarthome.network.AcProperty
import com.example.mysmarthome.network.LampApi
import com.example.mysmarthome.network.LampProperty
import com.example.mysmarthome.network.TvProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class OnOffDevicesViewModel(
    val arduinoId : Int,
    val deviceType: Int,
    val resources : Resources,
    application: Application
): AndroidViewModel(application) {

    private var viewModelJob= Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

//    private val _lampsList = MutableLiveData<List<LampProperty>>()
//    val lampsList : LiveData<List<LampProperty>>
//    get() = _lampsList
//
//    private val _acList = MutableLiveData<List<AcProperty>>()
//    val acList : LiveData<List<AcProperty>>
//        get() = _acList
//
//    private val _tvList = MutableLiveData<List<TvProperty>>()
//    val tvList : LiveData<List<TvProperty>>
//        get() = _tvList

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
            print("Started")
        when(deviceType){
            1 -> { val listResult = LampApi.retrofitService.getLampProperties(arduinoId)
                   var list:List<OnOffDevices> = emptyList()
                    listResult.forEach { list+= OnOffDevices(it.lamp_id,it.lamp_name,resources.getDrawable(R.drawable.laamp),it.device_type) }
            _devicesList.value = list
            }

            2   -> {val listResult = LampApi.retrofitService.getAcProperties(arduinoId)
                var list:List<OnOffDevices> = emptyList()
                listResult.forEach { list+= OnOffDevices(it.ac_id,it.ac_name,resources.getDrawable(R.drawable.condition),it.device_type,temp = it.ac_temp) }
                _devicesList.value = list}
            3 -> {val listResult =  LampApi.retrofitService.getTvProperties(arduinoId)
                var list:List<OnOffDevices> = emptyList()
                listResult.forEach { list+= OnOffDevices(it.tv_id,it.tv_name,resources.getDrawable(R.drawable.televation),it.device_type,channel = it.channel) }
                _devicesList.value = list}

        }} catch (e:Exception){print("Exception:"+e)}}

    }
}