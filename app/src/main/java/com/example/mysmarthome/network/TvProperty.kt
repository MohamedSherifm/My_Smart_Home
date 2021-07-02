package com.example.mysmarthome.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TvProperty (
    val tv_id: Int,
    val tv_name : String,
    val device_type: Int,
    val pin:Int,
    val status: Int,
    val channel: Int
): Parcelable
