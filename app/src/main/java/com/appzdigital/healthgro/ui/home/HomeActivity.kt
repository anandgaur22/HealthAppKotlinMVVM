package com.appzdigital.healthgro.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.appzdigital.healthgro.R
import com.appzdigital.healthgro.databinding.ActivityHomeBinding
import com.appzdigital.healthgro.ui.searchLocation.SearchLocationActivity
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewmodel: HomeViewModel
    private lateinit var binding: ActivityHomeBinding
    private var doubleBackToExitPressedOnce = false

    /* var fragment: Fragment? = null
     var fragmentManager: FragmentManager? = null
     var fragmentTransaction: FragmentTransaction? = null*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewmodel = ViewModelProvider(this).get(HomeViewModel::class.java)
        init()
    }

    fun init() {

        val frag: Fragment = HomeFragment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.frame_container, frag, "Home Fragment")
        transaction.commit()


        binding.selectLocation.setOnClickListener {


            val i = Intent(this@HomeActivity, SearchLocationActivity::class.java)
            startActivityForResult(i, 1)
        }

    }


    override fun onBackPressed() {
        val view: View = findViewById(android.R.id.content)
        if (supportFragmentManager.backStackEntryCount > 1) {
            super.onBackPressed()
        } else if (supportFragmentManager.backStackEntryCount == 0) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }
            this.doubleBackToExitPressedOnce = true
            Snackbar.make(view, "Press again to Exit", Snackbar.LENGTH_LONG).show()
            Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                val location = data!!.getStringExtra("location")

                binding.location.text = location
            }
        }
    }
}