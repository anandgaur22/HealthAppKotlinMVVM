package com.appzdigital.healthgro.network

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface BackEndApi {

    @POST("user/login")
    fun login(@Body body: JsonObject): Call<JsonObject>

    @POST("user/registration")
    fun registration(@Body body: JsonObject): Call<JsonObject>

    @POST("user/verifyMobileNumber")
    fun verifyMobileNumber(@Body body: JsonObject): Call<JsonObject>

    @POST("user/forgotPassword")
    fun forgotPassword(@Body body: JsonObject): Call<JsonObject>

    @POST("user/forgotverifyOtp")
    fun forgotverifyOtp(@Body body: JsonObject): Call<JsonObject>

    @POST("user/resendOtp")
    fun resendOtp(@Body body: JsonObject): Call<JsonObject>

    @POST("user/resetPassword")
    fun resetPassword(@Body body: JsonObject): Call<JsonObject>


}