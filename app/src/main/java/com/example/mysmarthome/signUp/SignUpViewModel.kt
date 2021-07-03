package com.example.mysmarthome.signUp

import android.app.Application
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysmarthome.network.LampApi
import com.example.mysmarthome.network.UserProperty
import com.squareup.moshi.JsonEncodingException
import kotlinx.coroutines.*

class SignUpViewModel: ViewModel() {


    private var  viewModelJob = Job()
    private val  uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _idCheckedUser = MutableLiveData<Boolean>()
    val idCheckedUser : LiveData<Boolean>
    get() = _idCheckedUser

    private val _navigateEvent = MutableLiveData<Boolean>()
    val navigateEvent : LiveData<Boolean>
    get() = _navigateEvent


    fun addUser(userName:String, userPassword:String, phoneNumber:Int , arduinoId:Int){
        uiScope.launch {


            val id = LampApi.retrofitService.getUserInfoByName(userName).get(0).user_id


            if(id != 0){
                _idCheckedUser.value = true
            }
            else{
                Log.d("Adding", "addUser: hello")
                addNewUser(userName, userPassword, phoneNumber, arduinoId)
                _navigateEvent.value = true
            }
        }
    }

    private suspend fun addNewUser(userName: String, userPassword: String, phoneNumber: Int, arduinoId: Int) {
         withContext(Dispatchers.IO){
            Log.d("Maybe Here", "addNewUser: Ok")
            LampApi.retrofitService.addNewUser(userName, userPassword, phoneNumber, arduinoId)
        }
    }



    fun onErrorShowed(){
        _idCheckedUser.value = null
    }

    fun onNavigationCompleted(){
        _navigateEvent.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    }


