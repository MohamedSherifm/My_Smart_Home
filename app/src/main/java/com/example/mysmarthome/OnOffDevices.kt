package com.example.mysmarthome

import android.content.res.Resources
import android.graphics.drawable.Drawable

data class OnOffDevices(val id:Int, val name:String, val Image:Drawable, val type: Int,val temp : Int=0,val channel : Int = 0)