package com.b2cinfosolution.healthgro.prefrences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class PrefrenceManager(var context: Context?) {

    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    var PRIVATE_MODE = 0

    fun saveResponseDetails(name: String?, user_type: String?, email: String?, phone: String?, userid: String?, token: String?) {
        editor = pref.edit()
        editor.putString("name", name)
        editor.putString("user_type", user_type)
        editor.putString("email", email)
        editor.putString("phone", phone)
        editor.putString("userid", userid)
        editor.putString("token", token)
        editor.commit()
    }

    fun saveCity(city: String?) {
        editor = pref.edit()
        editor.putString("city", city)
        editor.commit()
    }

    fun saveState(state: String?) {
        editor = pref.edit()
        editor.putString("state", state)
        editor.commit()
    }

    fun saveAddress(address: String?) {
        editor = pref.edit()
        editor.putString("address", address)
        editor.commit()
    }

    fun savePostCode(zip: String?) {
        editor = pref.edit()
        editor.putString("zip", zip)
        editor.commit()
    }

    fun saveLatitude(latitude: String?) {
        editor = pref.edit()
        editor.putString("latitude", latitude)
        editor.commit()
    }

    @SuppressLint("CommitPrefEdits")
    fun savePhone(phone: String?) {
        editor = pref.edit()
        editor.putString("phone", phone)
        editor.commit()
    }

    @SuppressLint("CommitPrefEdits")
    fun saveLongitude(longitude: String?) {
        editor = pref.edit()
        editor.putString("longitude", longitude)
        editor.commit()
    }

    /*User Details*/
    fun fetchFname(): String? {
        return pref.getString("firstname", "")
    }

    fun fetchLname(): String? {
        return pref.getString("lastname", "")
    }

    fun fetchEmail(): String? {
        return pref.getString("email", "")
    }

    fun fetchPhoneNo(): String? {
        return pref.getString("phone", "")
    }

    fun fetchUserId(): String? {
        return pref.getString("userid", "")
    }

    fun fetchZip(): String? {
        return pref.getString("zip", "")
    }

    fun fetchAddress(): String? {
        return pref.getString("address", "")
    }

    fun fetchState(): String? {
        return pref.getString("state", "")
    }

    fun fetchCity(): String? {
        return pref.getString("city", "")
    }

    fun validateSession(): Boolean {
        return pref.getBoolean(LOG_IN_OUT, false) == true
    }

    fun saveSessionLogin() {
        editor.putBoolean(LOG_IN_OUT, true)
        editor.commit()
    }

    val isUserLogedOut: Unit
        get() {
            editor = pref.edit()
            editor.putString("name", "")
            editor.putString("email", "")
            editor.putString("phone", "")
            editor.clear()
            editor.commit()
        }

    companion object {
        private const val PREF_NAME = "Booking App"
        private const val LOG_IN_OUT = "session"
    }

    init {
        pref = context?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)!!
        editor = pref.edit()
    }
}
