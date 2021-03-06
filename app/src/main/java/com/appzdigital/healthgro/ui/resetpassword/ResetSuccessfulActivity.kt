package com.appzdigital.healthgro.ui.resetpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.appzdigital.healthgro.R
import com.appzdigital.healthgro.databinding.ActivityResetSuccessfulBinding
import com.appzdigital.healthgro.ui.login.LoginActivity
import javax.inject.Inject

class ResetSuccessfulActivity : AppCompatActivity() {

    @Inject
    lateinit var viewmodel: ResetSuccessfulViewModel

    private lateinit var binding: ActivityResetSuccessfulBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_successful)
        viewmodel = ViewModelProvider(this).get(ResetSuccessfulViewModel::class.java)
        init()
    }

    fun init() {


        binding.Login.setOnClickListener {

            val i = Intent(this@ResetSuccessfulActivity, LoginActivity::class.java)
            startActivity(i)
            finish()
        }


    }
}