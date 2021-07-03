package com.example.mysmarthome.signUp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mysmarthome.databinding.ActivitySignUpBinding
import com.example.mysmarthome.signIn.MainActivity

class SignUp : AppCompatActivity() {
    lateinit var userName:String
    lateinit var password:String
    var phoneNumber:Int = 0
    var arduinoId:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySignUpBinding =    ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val signUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        binding.signUpViewModel = signUpViewModel

        binding.lifecycleOwner = this

        fun getInfo(){

        userName = binding.userNameTxt.getEditText()?.getText().toString()
        password = binding.passwordTxt.getEditText()?.getText().toString()
        phoneNumber = binding.phoneNumberTxt.getEditText()?.getText().toString().toInt()
        arduinoId = binding.arduinoIdTxt.getEditText()?.getText().toString().toInt()
        }

        binding.signUpBtn.setOnClickListener {
            getInfo()
            signUpViewModel.addUser(userName, password, phoneNumber, arduinoId)

        }

        signUpViewModel.idCheckedUser.observe(this , Observer { id ->
            if(id == true){
                Toast.makeText(this, "User name is taken",Toast.LENGTH_SHORT).show()
                signUpViewModel.onErrorShowed()
            }
        })

        signUpViewModel.navigateEvent.observe(this, Observer {
            if(it == true){
                Toast.makeText(this, "user Added",Toast.LENGTH_SHORT).show()
                val intent = Intent(this , MainActivity::class.java)
                startActivity(intent)
                signUpViewModel.onNavigationCompleted()
            }
        })
    }
}