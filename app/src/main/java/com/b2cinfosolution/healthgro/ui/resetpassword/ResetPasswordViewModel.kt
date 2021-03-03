package com.b2cinfosolution.healthgro.ui.resetpassword

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

class ResetPasswordViewModel(application: Application) : AndroidViewModel(application),
    Callback<JSONObject> {


    var progressDialog: SingleLiveEvent<Boolean>? = null
    var resetPassword: MutableLiveData<JSONObject>? = null
    var resetPasswordError: MutableLiveData<JSONObject>? = null

    init {
        progressDialog = SingleLiveEvent<Boolean>()
        resetPassword = MutableLiveData()
        resetPasswordError = MutableLiveData<JSONObject>()
    }

    fun resetPassword(res_token: String,newPassword: String,confirmPassword: String) {

        val request = JsonObject()
        request.addProperty("res_token", res_token)
        request.addProperty("newPassword", newPassword)
        request.addProperty("confirmPassword", confirmPassword)

        progressDialog?.value = true
        val call2 =
            WebServiceClient.client.create(BackEndApi::class.java).resetPassword(request)
        call2.enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (!response.isSuccessful) {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    progressDialog?.value = false
                    resetPasswordError?.value = jObjError

                } else {
                    val gson =
                        JsonParser().parse(response.body().toString()).asJsonObject
                    val jsonObj = JSONObject(gson.toString())
                    resetPassword?.value = jsonObj
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