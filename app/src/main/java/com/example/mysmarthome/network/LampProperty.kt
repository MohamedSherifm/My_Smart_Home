package com.example.mysmarthome.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class LampProperty (
    val lamp_id: Int,
    val lamp_name:String,
    val device_type: Int,
    val pin:Int,
    val status: Int
        ):Parcelable
