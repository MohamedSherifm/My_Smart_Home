package com.example.mysmarthome

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysmarthome.network.CategoriesTypes

import com.example.mysmarthome.network.HomeApi
import com.example.mysmarthome.network.LampApi
import com.example.mysmarthome.network.LampProperty
import kotlinx.coroutines.*

class MainViewModel: ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope (Dispatchers.Main + viewModelJob)

    private val _properties = MutableLiveData<List<LampProperty>>()

    val properties : LiveData<List<LampProperty>>
        get()= _properties

    private val _categories = MutableLiveData<List<CategoriesTypes>>()
    val categories: LiveData<List<CategoriesTypes>>
    get() = _categories

    private val _listOfCategories = MutableLiveData<List<Int>>()
    val listOfCategories:LiveData<List<Int>>
    get() = _listOfCategories

    init{
        getLampInfo(1)
    }

    private fun getLampInfo(arduinoId : Int) {
        coroutineScope.launch{
            try {
                var listResult = LampApi.retrofitService.getCategories(arduinoId)
                _categories.value = listResult
                var list:List<Int> = emptyList()
                listResult.forEach { list+=it.device_type }
                list = list.distinct().toList()
                _listOfCategories.value = list

            }
            catch (e:Exception){
                println("Failure: "+ e.message)
            }
        }
    }

}