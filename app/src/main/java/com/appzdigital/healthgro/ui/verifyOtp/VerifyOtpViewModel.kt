package com.appzdigital.healthgro.ui.verifyOtp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.appzdigital.healthgro.network.BackEndApi
import com.appzdigital.healthgro.network.WebServiceClient
import com.appzdigital.healthgro.utils.SingleLiveEvent
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyOtpViewModel(application: Application) : AndroidViewModel(application),
    Callback<JSONObject> {


    var progressDialog: SingleLiveEvent<Boolean>? = null
    var verifyMobile: MutableLiveData<JSONObject>? = null
    var forgotverifyOtp: MutableLiveData<JSONObject>? = null
    var resendOtp: MutableLiveData<JSONObject>? = null
    var verifyMobileError: MutableLiveData<JSONObject>? = null
    var resendOtpError: MutableLiveData<JSONObject>? = null
    var forgotverifyOtpError: MutableLiveData<JSONObject>? = null

    init {
        progressDialog = SingleLiveEvent<Boolean>()
        verifyMobile = MutableLiveData()
        resendOtp = MutableLiveData()
        forgotverifyOtp = MutableLiveData()
        verifyMobileError = MutableLiveData<JSONObject>()
        resendOtpError = MutableLiveData<JSONObject>()
        forgotverifyOtpError = MutableLiveData<JSONObject>()
    }

    fun verifyMobile(res_token: String, otp: String) {

        val request = JsonObject()
        request.addProperty("res_token", res_token)
        request.addProperty("otp", otp)

        progressDialog?.value = true
        val call2 =
            WebServiceClient.client.create(BackEndApi::class.java).verifyMobileNumber(request)
        call2.enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (!response.isSuccessful) {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    progressDialog?.value = false
                    verifyMobileError?.value = jObjError

                } else {
                    val gson =
                        JsonParser().parse(response.body().toString()).asJsonObject
                    val jsonObj = JSONObject(gson.toString())
                    verifyMobile?.value = jsonObj
                    progressDialog?.value = false
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                call.cancel()
                Log.e("responce==", t.toString())
                progressDialog?.value = false
            }
        })

    }


    fun forgotverifyOtp(res_token: String, otp: String) {

        val request = JsonObject()
        request.addProperty("res_token", res_token)
        request.addProperty("otp", otp)

        progressDialog?.value = true
        val call2 =
            WebServiceClient.client.create(BackEndApi::class.java).forgotverifyOtp(request)
        call2.enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (!response.isSuccessful) {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    progressDialog?.value = false
                    forgotverifyOtpError?.value = jObjError

                } else {
                    val gson =
                        JsonParser().parse(response.body().toString()).asJsonObject
                    val jsonObj = JSONObject(gson.toString())
                    forgotverifyOtp?.value = jsonObj
                    progressDialog?.value = false
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                call.cancel()
                Log.e("responce==", t.toString())
                progressDialog?.value = false
            }
        })

    }



    fun resendOtp(res_token: String) {

        val request = JsonObject()
        request.addProperty("res_token", res_token)

        progressDialog?.value = true
        val call2 =
            WebServiceClient.client.create(BackEndApi::class.java).resendOtp(request)
        call2.enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (!response.isSuccessful) {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    progressDialog?.value = false
                    resendOtpError?.value = jObjError

                } else {
                    val gson =
                        JsonParser().parse(response.body().toString()).asJsonObject
                    val jsonObj = JSONObject(gson.toString())
                    resendOtp?.value = jsonObj
                    progressDialog?.value = false
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                call.cancel()
                Log.e("responce==", t.toString())
                progressDialog?.value = false
            }
        })

    }


    override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
        progressDialog?.value = false

    }

    override fun onFailure(call: Call<JSONObject>, t: Throwable) {
        progressDialog?.value = false
    }

}