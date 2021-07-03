package com.example.mysmarthome.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysmarthome.network.LampApi
import kotlinx.coroutines.*

class SignInViewModel : ViewModel(){

    private var  viewModelJob = Job()
    private val  uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateEvent = MutableLiveData<Boolean>()
    val navigateEvent : LiveData<Boolean>
        get() = _navigateEvent

    private val _idCheckedUser = MutableLiveData<Boolean>()
    val idCheckedUser : LiveData<Boolean>
        get() = _idCheckedUser

    private val _arduinoId = MutableLiveData<Int>()
    val arduinoId : LiveData<Int>
    get() = _arduinoId

    fun logIn(userName: String, userPassword:String){
        uiScope.launch{
            val arId = logInUser(userName,userPassword)
            if(arId != 0){
                _navigateEvent.value = true
                _arduinoId.value = arId
            }
            else{
                _idCheckedUser.value = true
            }
        }
    }

    private suspend fun logInUser(userName: String, userPassword: String):Int {
        return withContext(Dispatchers.IO){
            val list = LampApi.retrofitService.getUserInfo(userName, userPassword)
            return@withContext list.get(0).arduino_id
        }
    }

    fun onNavigationCompleted(){
        _navigateEvent.value = null
    }

    fun onErrorShown(){
        _idCheckedUser.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}