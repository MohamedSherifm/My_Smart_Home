package com.example.mysmarthome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider




class MainActivity : AppCompatActivity() {

    companion object{
        var globalVar = 1
        var changed = MutableLiveData<Int>()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val test: TextView
        test = findViewById(R.id.test_Text)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.listOfCategories.observe(this, Observer { categories ->

            categories.forEach { println(it) }
            test.setText(categories.size.toString())

        })

        val intent = Intent(this,AppActivity::class.java)
        startActivity(intent)
    }
}