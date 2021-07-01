package com.example.mysmarthome.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AcProperty (
    val ac_id: Int,
    val device_type: Int,
    val pin:Int,
    val status: Int,
    val ac_temp: Int
): Parcelable
