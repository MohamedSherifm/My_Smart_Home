package com.example.mysmarthome.signIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mysmarthome.AppActivity
import com.example.mysmarthome.MainViewModel
import com.example.mysmarthome.R
import com.example.mysmarthome.databinding.ActivityMainBinding
import com.example.mysmarthome.signUp.SignUp


class MainActivity : AppCompatActivity() {
    lateinit var userName:String
    lateinit var password:String
    companion object{
        var globalVar = 1

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val signInViewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        binding.signInViewModel = signInViewModel

        binding.lifecycleOwner = this

        fun getInfo(){
            userName = binding.username.getEditText()?.getText().toString()
            password = binding.password.getEditText()?.getText().toString()
        }

        binding.loginBtn.setOnClickListener {
            getInfo()
            signInViewModel.logIn(userName, password)
        }

        signInViewModel.idCheckedUser.observe(this , Observer { id ->
            if(id == true){
                Toast.makeText(this, "Wrong user or password", Toast.LENGTH_SHORT).show()
                signInViewModel.onErrorShown()
            }
        })

        signInViewModel.arduinoId.observe(this , Observer {
            if(it != 0){
                globalVar = it
            }
        })

            signInViewModel.navigateEvent.observe(this, Observer {
            if(it == true){
                Toast.makeText(this, "user Added",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, AppActivity::class.java)
                startActivity(intent)
                signInViewModel.onNavigationCompleted()
            }
        })

        binding.signupScreen.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }





    }
}