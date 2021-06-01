package com.b2cinfosolution.healthgro.ui.verifyOtp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.b2cinfosolution.healthgro.R
import com.b2cinfosolution.healthgro.databinding.ActivitySignupBinding
import com.b2cinfosolution.healthgro.databinding.ActivityVerifyOtpBinding
import com.b2cinfosolution.healthgro.prefrences.PrefrenceManager
import com.b2cinfosolution.healthgro.ui.home.HomeActivity
import com.b2cinfosolution.healthgro.ui.resetpassword.ResetPasswordActivity
import com.b2cinfosolution.healthgro.ui.signup.SignupViewModel
import com.b2cinfosolution.healthgro.utils.CustomeProgressDialog
import com.b2cinfosolution.healthgro.utils.Utility
import javax.inject.Inject

class VerifyOtpActivity : AppCompatActivity() {

    @Inject
    lateinit var viewmodel: VerifyOtpViewModel
    private lateinit var binding: ActivityVerifyOtpBinding
    var customeProgressDialog: CustomeProgressDialog? = null
    private var prefrenceManager: PrefrenceManager? = null
    private var token: String? = null
    private var screen: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_otp)
        viewmodel = ViewModelProvider(this).get(VerifyOtpViewModel::class.java)
        init()
    }

    fun init() {
        prefrenceManager = PrefrenceManager(applicationContext)
        customeProgressDialog = CustomeProgressDialog(this)

        val intent = intent
        token = intent.getStringExtra("token")!!
        screen = intent.getStringExtra("screen")!!

        countdownTime()
        Textwatcher()

        initObservables()

        binding.verify.setOnClickListener {

            val a: String = binding.et1.getText().toString().trim { it <= ' ' }
            val b: String = binding.et2.getText().toString().trim { it <= ' ' }
            val c: String = binding.et3.getText().toString().trim { it <= ' ' }
            val d: String = binding.et4.getText().toString().trim { it <= ' ' }

            val otp = a + b + c + d

            if (screen.equals("1")){
                viewmodel.verifyMobile(token!!, otp)

            }else{
                viewmodel.forgotverifyOtp(token!!, otp)
            }
        }

        binding.resendOtp.setOnClickListener {
            countdownTime()
            binding.resendOtp.setVisibility(View.GONE)

            viewmodel.resendOtp(token!!)

        }


    }

    private fun initObservables() {
        viewmodel.progressDialog?.observe(this, Observer {
            if (it!!) customeProgressDialog?.show() else customeProgressDialog?.dismiss()

        })

        viewmodel.verifyMobile?.observe(this, Observer { user ->

            Log.e("verifyMobile_responce==", user.toString())

            if (user.getString("status").equals("true")) {

                val msg = user.getString("message")
                Utility.customeToastGreenBottom(msg, this)

                prefrenceManager?.saveSessionLogin()
                val intent = Intent(this@VerifyOtpActivity, HomeActivity::class.java)
                startActivity(intent)

            } else {
                val msg = user.getString("message")
                Utility.customeToastRedBottom(msg, this)
            }
        })

      viewmodel.forgotverifyOtp?.observe(this, Observer { user ->

            Log.e("forgotverifyOtp_resp==", user.toString())

            if (user.getString("status").equals("true")) {

                val msg = user.getString("message")
                val data = user.getString("data")

                val intent = Intent(this@VerifyOtpActivity, ResetPasswordActivity::class.java)
                intent.putExtra("token", data)
                startActivity(intent)

            } else {
                val msg = user.getString("message")
                Utility.customeToastRedBottom(msg, this)
            }
        })

        viewmodel.resendOtp?.observe(this, Observer { user ->

            Log.e("resendOtp_responce==", user.toString())

            if (user.getString("status").equals("true")) {

                val msg = user.getString("message")
                token = user.getString("data")

                Utility.customeToastGreenBottom(msg, this)


            } else {
                val msg = user.getString("message")
                Utility.customeToastRedBottom(msg, this)
            }
        })


    }


    fun countdownTime() {
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timeTv.setText("00:" + millisUntilFinished / 1000 + " sec")
                //here you can have your logic to set text to edittext
                binding.resendOtp.setVisibility(View.GONE)
                // binding.leftTimeLl.setVisibility(View.VISIBLE)
            }

            override fun onFinish() {
                binding.resendOtp.setVisibility(View.VISIBLE)
                //binding.leftTimeLl.setVisibility(View.GONE)
            }
        }.start()
    }

    fun Textwatcher() {
        binding.et1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.length == 1) {
                    binding.et2.requestFocus()
                } else if (s.length == 0) {
                    binding.et1.clearFocus()
                }
            }
        })
        binding.et2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.length == 1) {
                    binding.et3.requestFocus()
                } else if (s.length == 0) {
                    binding.et1.requestFocus()
                }
            }
        })
        binding.et3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.length == 1) {
                    binding.et4.requestFocus()
                } else if (s.length == 0) {
                    binding.et2.requestFocus()
                }
            }
        })

        binding.et4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.length == 1) {
                    // et6.clearFocus();
                } else if (s.length == 0) {
                    binding.et4.requestFocus()
                }
            }
        })
    }

}