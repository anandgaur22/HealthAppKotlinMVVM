package com.appzdigital.healthgro.ui.forgotpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.appzdigital.healthgro.R
import com.appzdigital.healthgro.databinding.ActivityForgotPasswordBinding
import com.appzdigital.healthgro.prefrences.PrefrenceManager
import com.appzdigital.healthgro.ui.verifyOtp.VerifyOtpActivity
import com.appzdigital.healthgro.utils.CustomeProgressDialog
import com.appzdigital.healthgro.utils.Utility
import javax.inject.Inject

class ForgotPasswordActivity : AppCompatActivity() {

    @Inject
    lateinit var viewmodel: ForgotPasswordViewModel
    private lateinit var binding: ActivityForgotPasswordBinding
    var customeProgressDialog: CustomeProgressDialog? = null
    private var prefrenceManager: PrefrenceManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        viewmodel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
        init()
    }

    fun init() {

        prefrenceManager = PrefrenceManager(applicationContext)
        customeProgressDialog = CustomeProgressDialog(this)

        initObservables()

        binding.sendCode.setOnClickListener {

            val mobileNo = binding.mobileNo.text.toString()

            if (mobileNo.isEmpty()) {
                binding.mobileNo.setError("Mobile no. is required")
                binding.mobileNo.requestFocus()
            } else {
                viewmodel.forgotPassword(mobileNo)
            }
        }
    }

    private fun initObservables() {
        viewmodel.progressDialog?.observe(this, Observer {
            if (it!!) customeProgressDialog?.show() else customeProgressDialog?.dismiss()

        })

        viewmodel.forgotPassword?.observe(this, Observer { user ->

            Log.e("forgotPass_responce==", user.toString())

            if (user.getString("status").equals("true")) {

                val msg = user.getString("message")
                val data = user.getString("data")

                val intent = Intent(this@ForgotPasswordActivity, VerifyOtpActivity::class.java)
                intent.putExtra("token", data)
                intent.putExtra("screen", "2")
                startActivity(intent)

            } else {
                val msg = user.getString("message")
                Utility.customeToastRedBottom(msg, this)
            }
        })
    }


}