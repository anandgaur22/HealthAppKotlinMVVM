package com.appzdigital.healthgro.ui.login

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


class LoginViewModel(application: Application) : AndroidViewModel(application),
    Callback<JSONObject> {


    var progressDialog: SingleLiveEvent<Boolean>? = null
    var userLogin: MutableLiveData<JSONObject>? = null
    var userLoginError: MutableLiveData<JSONObject>? = null

    init {
        progressDialog = SingleLiveEvent<Boolean>()
        userLogin = MutableLiveData()
        userLoginError = MutableLiveData<JSONObject>()
    }

    fun login( mobileNumber: String,password: String) {

        val request = JsonObject()
        request.addProperty("mobileNumber", mobileNumber)
        request.addProperty("password", password)

        progressDialog?.value = true
        val call2 = WebServiceClient.client.create(BackEndApi::class.java).login(request)
        call2.enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (!response.isSuccessful) {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    progressDialog?.value = false
                    userLoginError?.value = jObjError

                } else {
                    val gson =
                        JsonParser().parse(response.body().toString()).asJsonObject
                    val jsonObj = JSONObject(gson.toString())
                    userLogin?.value = jsonObj
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