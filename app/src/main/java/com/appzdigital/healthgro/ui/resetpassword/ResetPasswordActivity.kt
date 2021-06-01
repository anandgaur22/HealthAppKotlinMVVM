package com.appzdigital.healthgro.ui.resetpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.appzdigital.healthgro.R
import com.appzdigital.healthgro.databinding.ActivityResetPasswordBinding
import com.appzdigital.healthgro.prefrences.PrefrenceManager
import com.appzdigital.healthgro.utils.CustomeProgressDialog
import com.appzdigital.healthgro.utils.Utility
import javax.inject.Inject

class ResetPasswordActivity : AppCompatActivity() {

    @Inject
    lateinit var viewmodel: ResetPasswordViewModel
    private lateinit var binding: ActivityResetPasswordBinding
    var customeProgressDialog: CustomeProgressDialog? = null
    private var prefrenceManager: PrefrenceManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password)
        viewmodel = ViewModelProvider(this).get(ResetPasswordViewModel::class.java)
        init()
    }

    fun init() {

        val intent = intent
        val token = intent.getStringExtra("token")!!

        prefrenceManager = PrefrenceManager(applicationContext)
        customeProgressDialog = CustomeProgressDialog(this)

        initObservables()

        binding.submit.setOnClickListener {

            val password = binding.passwordEt.text.toString()
            val cnfpassword = binding.cnfPassword.text.toString()

            if (password.isEmpty()) {
                binding.passwordEt.setError("Password is required")
                binding.passwordEt.requestFocus()
            }else if (cnfpassword.isEmpty()) {
                binding.cnfPassword.setError("Confirm password is required")
                binding.cnfPassword.requestFocus()
            } else {
                viewmodel.resetPassword(token,password,cnfpassword)
            }
        }


    }

    private fun initObservables() {
        viewmodel.progressDialog?.observe(this, Observer {
            if (it!!) customeProgressDialog?.show() else customeProgressDialog?.dismiss()

        })

        viewmodel.resetPassword?.observe(this, Observer { user ->

            Log.e("resetPass_responce==", user.toString())

            if (user.getString("status").equals("true")) {

                val msg = user.getString("message")

                val i = Intent(this@ResetPasswordActivity, ResetSuccessfulActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(i)
                finish()

            } else {
                val msg = user.getString("message")
                Utility.customeToastRedBottom(msg, this)
            }
        })
    }


}