package com.example.mysmarthome.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserProperty (
    val user_id: Int,
    val user_name:String,
    val user_password: String,
    val user_phoneNumber: String,
    val user_mail:String,
    val arduino_id:Int
): Parcelable
