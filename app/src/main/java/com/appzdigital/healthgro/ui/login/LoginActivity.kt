package com.appzdigital.healthgro.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.appzdigital.healthgro.R
import com.appzdigital.healthgro.databinding.ActivityLoginBinding
import com.appzdigital.healthgro.prefrences.PrefrenceManager
import com.appzdigital.healthgro.ui.forgotpassword.ForgotPasswordActivity
import com.appzdigital.healthgro.ui.home.HomeActivity
import com.appzdigital.healthgro.ui.signup.SignupActivity
import com.appzdigital.healthgro.utils.CustomeProgressDialog
import com.appzdigital.healthgro.utils.Utility.customeToastGreenBottom
import com.appzdigital.healthgro.utils.Utility.customeToastRedBottom
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var viewmodel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private var prefrenceManager: PrefrenceManager? = null
    var customeProgressDialog: CustomeProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewmodel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.vm = viewmodel

        init()
    }

    fun init() {

        prefrenceManager = PrefrenceManager(applicationContext)
        customeProgressDialog = CustomeProgressDialog(this)
        initObservables()

        binding.login.setOnClickListener {

            val mobile_no = binding.mobileNoEt.text.toString()
            val password = binding.passwordEt.text.toString()

            if (mobile_no.isEmpty()) {
                binding.mobileNoEt.setError("Mobile No. is required")
                binding.mobileNoEt.requestFocus()
            } else if (password.isEmpty()) {
                binding.passwordEt.setError("Password is required")
                binding.passwordEt.requestFocus()
            } else {

                viewmodel.login(mobile_no, password)

            }


        }

        binding.signUp.setOnClickListener {


            val i = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(i)
        }

        binding.forgotPassword.setOnClickListener {


            val i = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(i)
        }


    }

    private fun initObservables() {
        viewmodel.progressDialog?.observe(this, Observer {
            if (it!!) customeProgressDialog?.show() else customeProgressDialog?.dismiss()

        })

        viewmodel.userLogin?.observe(this, Observer { user ->

            Log.e("login_responce==", user.toString())

            if (user.getString("status").equals("true")) {

                val msg = user.getString("message")

                val jsonObject = user.getJSONObject("data")

                val userid = jsonObject.getString("id")
                val token = jsonObject.getString("token")
                val name = jsonObject.getString("name")
                val user_type = jsonObject.getString("user_type")
                val email = jsonObject.getString("email")
                val mobileNumber = jsonObject.getString("mobileNumber")
                var password = jsonObject.getString("password")

                prefrenceManager?.saveResponseDetails(
                    name,
                    user_type,
                    email,
                    mobileNumber,
                    userid,
                    token
                )
                customeToastGreenBottom(msg, this)
                prefrenceManager?.saveSessionLogin()
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent)

            } else {
                val msg = user.getString("message")
                customeToastRedBottom(msg, this)
            }
        })
    }

}