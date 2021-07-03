package com.example.mysmarthome.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


private const val BASE_URL = "http://192.168.1.28/smart_home/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL).build()

interface HomeApi{
    @GET("getLampInfo.php")
    suspend fun getLampProperties(@Query("arduinoId")type: Int):
            List<LampProperty>

    @GET("getTvInfo.php")
    suspend fun getTvProperties(@Query("arduinoId") type: Int):
            List<TvProperty>

    @GET("getAcInfo.php")
    suspend fun getAcProperties(@Query("arduinoId") type: Int):
            List<AcProperty>

    @GET("getUserInfo.php")
    suspend fun getUserInfo(@Query("userName")userName: String, @Query("userPassword")userPassword:Int):
            List<UserProperty>

    @GET("getCategories.php")
    suspend fun getCategories(@Query("arduinoId")type:Int):
            List<CategoriesTypes>

    @GET("getDevicePins.php")
    suspend fun getDevicePins(@Query("table")type:String,@Query("arduinoId")arduinoId:Int):
            List<devicePins>

    @GET("addLamp.php")
    suspend fun addLamp(@Query("lampName")lampName:String,@Query("lampPin")pin:Int,@Query("arduinoId")id:Int)

    @GET("addAc.php")
    suspend fun addAc(@Query("acName")lampName:String,@Query("acPin")pin:Int,@Query("arduinoId")id:Int)

    @GET("addTv.php")
    suspend fun addTv(@Query("tvName")lampName:String,@Query("tvPin")pin:Int,@Query("arduinoId")id:Int)

    @GET("updateLampStatus.php")
    suspend fun updateLampStatus(@Query("lampId")lampId:Int,@Query("lampPin")lampPin:Int,@Query("arduinoId")arduinoId: Int,@Query("onAndoff")status:Int)

    @GET("updateAcStatus.php")
    suspend fun updateAcStatus(@Query("acId")acId:Int,@Query("acPin")acPin:Int,@Query("arduinoId")arduinoId: Int,@Query("onAndoff")status:Int)

    @GET("updateTvStatus.php")
    suspend fun updateTvStatus(@Query("tvId")tvId:Int,@Query("tvPin")tvPin:Int,@Query("arduinoId")arduinoId: Int,@Query("onAndoff")status:Int)

    @GET("deleteLamp.php")
    suspend fun deleteLamp(@Query("table")tableName:String,@Query("deviceId")deviceId:Int)

    @GET("updateAcTemp.php")
    suspend fun updateAcTemp(@Query("acId")acId:Int,@Query("acPin")acPin:Int,@Query("arduinoId")arduinoId: Int,@Query("acTemp")temp:Int)

    @GET("updateTvChannel.php")
    suspend fun updateTvChannel(@Query("tvId")tvId:Int,@Query("tvPin")tvPin:Int,@Query("arduinoId")arduinoId: Int,@Query("tvChannel")channel:Int)

    @GET("deleteAc.php")
    suspend fun deleteAc(@Query("table")tableName:String,@Query("deviceId")deviceId:Int)

    @GET("deleteTv.php")
    suspend fun deleteTv(@Query("table")tableName:String,@Query("deviceId")deviceId:Int)
}
object LampApi {
    val retrofitService : HomeApi by lazy {
        retrofit.create(HomeApi::class.java)
    }
}