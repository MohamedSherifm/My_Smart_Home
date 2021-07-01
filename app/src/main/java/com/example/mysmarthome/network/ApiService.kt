package com.example.mysmarthome.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
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
}
object LampApi {
    val retrofitService : HomeApi by lazy {
        retrofit.create(HomeApi::class.java)
    }
}