package com.b2cinfosolution.healthgro.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.b2cinfosolution.healthgro.R
import com.b2cinfosolution.healthgro.databinding.ActivitySplashBinding
import com.b2cinfosolution.healthgro.prefrences.PrefrenceManager
import com.b2cinfosolution.healthgro.ui.home.HomeActivity
import com.b2cinfosolution.healthgro.ui.login.LoginActivity
import com.b2cinfosolution.healthgro.utils.SPLASH_DELAY
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewmodel: SplashViewModel

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        viewmodel = ViewModelProvider(this).get(SplashViewModel::class.java)
        init()
    }

    fun init() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        Handler(Looper.getMainLooper()).postDelayed({
            val prefManager = PrefrenceManager(applicationContext)

            if (prefManager.validateSession()) {

                val i = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(i)
                finish()

            } else {
                val i = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(i)
                finish()
            }

        }, SPLASH_DELAY.toLong())

    }

}