package com.b2cinfosolution.healthgro.ui.signup

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.b2cinfosolution.healthgro.network.BackEndApi
import com.b2cinfosolution.healthgro.network.WebServiceClient
import com.b2cinfosolution.healthgro.utils.SingleLiveEvent
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel(application: Application) : AndroidViewModel(application),
    Callback<JSONObject> {


    var progressDialog: SingleLiveEvent<Boolean>? = null
    var userSignup: MutableLiveData<JSONObject>? = null
    var userSignupError: MutableLiveData<JSONObject>? = null

    init {
        progressDialog = SingleLiveEvent<Boolean>()
        userSignup = MutableLiveData()
        userSignupError = MutableLiveData<JSONObject>()
    }

    fun register( fstname: String,lastName: String,mobileNumber: String,email: String,password: String,confirm_password: String,gender: String,user_type: String,DOB: String) {

        val request = JsonObject()
        request.addProperty("name", fstname)
        request.addProperty("lastName", lastName)
        request.addProperty("mobileNumber", mobileNumber)
        request.addProperty("email", email)
        request.addProperty("password", password)
        request.addProperty("confirm_password", confirm_password)
        request.addProperty("gender", gender)
        request.addProperty("user_type", user_type)
        request.addProperty("DOB", DOB)

        progressDialog?.value = true
        val call2 = WebServiceClient.client.create(BackEndApi::class.java).registration(request)
        call2.enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (!response.isSuccessful) {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    progressDialog?.value = false
                    userSignupError?.value = jObjError

                } else {
                    val gson =
                        JsonParser().parse(response.body().toString()).asJsonObject
                    val jsonObj = JSONObject(gson.toString())
                    userSignup?.value = jsonObj
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