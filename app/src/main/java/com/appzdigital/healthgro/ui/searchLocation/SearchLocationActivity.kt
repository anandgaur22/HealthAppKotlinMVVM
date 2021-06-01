package com.b2cinfosolution.healthgro.ui.searchLocation

import android.R.attr.data
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.b2cinfosolution.healthgro.R
import com.b2cinfosolution.healthgro.databinding.ActivitySearchLocationBinding
import javax.inject.Inject


class SearchLocationActivity : AppCompatActivity() {

    @Inject
    lateinit var viewmodel: SearchLocationViewModel

    private lateinit var binding: ActivitySearchLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_location)
        viewmodel = ViewModelProvider(this).get(SearchLocationViewModel::class.java)
        init()
    }

    fun init() {

        val intent = Intent()

        binding.banglore.setOnClickListener {

            intent.putExtra("location", "Bangalore")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.hydrbad.setOnClickListener {

            intent.putExtra("location", "Hyderabad")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.chennai.setOnClickListener {

            intent.putExtra("location", "Chennai")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.mumbai.setOnClickListener {

            intent.putExtra("location", "Mumbai")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.delhi.setOnClickListener {

            intent.putExtra("location", "Delhi")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.pune.setOnClickListener {

            intent.putExtra("location", "Pune")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }


    }
}