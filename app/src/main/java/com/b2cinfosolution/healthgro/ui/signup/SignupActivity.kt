package com.b2cinfosolution.healthgro.ui.signup

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.b2cinfosolution.healthgro.R
import com.b2cinfosolution.healthgro.databinding.ActivitySignupBinding
import com.b2cinfosolution.healthgro.prefrences.PrefrenceManager
import com.b2cinfosolution.healthgro.ui.verifyOtp.VerifyOtpActivity
import com.b2cinfosolution.healthgro.utils.CustomeProgressDialog
import com.b2cinfosolution.healthgro.utils.Utility
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class SignupActivity : AppCompatActivity() {

    @Inject
    lateinit var viewmodel: SignupViewModel

    private lateinit var binding: ActivitySignupBinding
    var dialog: AlertDialog? = null
    private var prefrenceManager: PrefrenceManager? = null
    var customeProgressDialog: CustomeProgressDialog? = null
    private var dob: String? = ""
    private var gender: String? = ""
    private var mDateSetListener: OnDateSetListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        viewmodel = ViewModelProvider(this).get(SignupViewModel::class.java)
        init()
    }

    fun init() {
        prefrenceManager = PrefrenceManager(applicationContext)
        customeProgressDialog = CustomeProgressDialog(this)
        initObservables()



        binding.Login.setOnClickListener {
            onBackPressed()
        }

        binding.untick.setOnClickListener {
            binding.tick.visibility = View.VISIBLE
            binding.untick.visibility = View.GONE

        }

        binding.tick.setOnClickListener {

            binding.untick.visibility = View.VISIBLE
            binding.tick.visibility = View.GONE

        }

        binding.signUp.setOnClickListener {
            val fstName = binding.fstName.text.toString()
            val lstName = binding.lstName.text.toString()
            val mobileNo = binding.mobileNo.text.toString()
            val email = binding.email.text.toString()
            val passworrd = binding.passworrd.text.toString()
            val cnfPassword = binding.cnfPassword.text.toString()

            if (fstName.isEmpty()) {
                binding.fstName.setError("First name is required")
                binding.fstName.requestFocus()
            } else if (lstName.isEmpty()) {
                binding.lstName.setError("Last name is required")
                binding.lstName.requestFocus()
            } else if (gender.equals("")) {
                Utility.customeToastRedBottom("Please select gender first", this)

            } else if (dob!!.isEmpty()) {
                Utility.customeToastRedBottom("Please select dob first", this)
            } else if (mobileNo.isEmpty()) {
                binding.mobileNo.setError("Mobile no. is required")
                binding.mobileNo.requestFocus()
            } else if (email.isEmpty()) {
                binding.email.setError("Email is required")
                binding.email.requestFocus()
            } else if (passworrd.isEmpty()) {
                binding.passworrd.setError("Password is required")
                binding.passworrd.requestFocus()
            } else if (cnfPassword.isEmpty()) {
                binding.cnfPassword.setError("Confirm password is required")
                binding.cnfPassword.requestFocus()
            } else {

                viewmodel.register(
                    fstName,
                    lstName,
                    mobileNo,
                    email,
                    passworrd,
                    cnfPassword,
                    gender.toString(),
                    "1",
                    dob!!
                )

            }
        }

        binding.termsCondition.setOnClickListener {
            terms_conditions_dilogs()
        }

        binding.dobIv.setOnClickListener {

            dob_pickDate()
        }

        binding.selectGender.setOnClickListener {

            gender_dilog()
        }


    }


    private fun initObservables() {
        viewmodel.progressDialog?.observe(this, Observer {
            if (it!!) customeProgressDialog?.show() else customeProgressDialog?.dismiss()

        })

        viewmodel.userSignup?.observe(this, Observer { user ->

            Log.e("registration_responce==", user.toString())

            if (user.getString("status").equals("true")) {

                val msg = user.getString("message")
                val data = user.getString("data")

                val intent = Intent(this@SignupActivity, VerifyOtpActivity::class.java)
                intent.putExtra("token", data)
                intent.putExtra("screen", "1")
                startActivity(intent)

            } else {
                val msg = user.getString("message")
                Utility.customeToastRedBottom(msg, this)
            }
        })
    }


    fun terms_conditions_dilogs() {

        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.terms_conditions_dilogs, null)

        val tick: ImageView = dialogView.findViewById(R.id.tick)
        val untick: ImageView = dialogView.findViewById(R.id.untick)
        val submit: CardView = dialogView.findViewById(R.id.submit)

        builder.setView(dialogView)
        dialog = builder.create()
        dialog?.getWindow()!!.attributes.windowAnimations = R.style.animationdialog

        submit.setOnClickListener { // Dismiss the alert dialog

            dialog?.dismiss()
        }


        dialog?.show()
    }

    fun dob_pickDate() {
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
        mDateSetListener =
            OnDateSetListener { datePicker, year, month, day -> //month = month + 1;
                Log.d("TAG", "onDateSet: mm/dd/yyy: $month/$day/$year")

                val months = month + 1

                dob = "$day-$months-$year"

                binding.dob.setText(formatDate(year, month, day))

            }
        val dialog = DatePickerDialog(
            this,
            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            mDateSetListener,
            year,
            month,
            day
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun formatDate(year: Int, month: Int, day: Int): String? {
        val cal: Calendar = Calendar.getInstance()
        cal.setTimeInMillis(0)
        cal.set(year, month, day)
        val date: Date = cal.getTime()
        val sdf = SimpleDateFormat("dd MMMM yyyy")
        return sdf.format(date)
    }


    fun gender_dilog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView: View = inflater.inflate(R.layout.gender_type_dilogs, null)
        val close_iv: ImageView = dialogView.findViewById(R.id.close_iv)
        val male_cv: CardView = dialogView.findViewById(R.id.male_cv)
        val female_cv: CardView = dialogView.findViewById(R.id.female_cv)

        builder.setView(dialogView)
        val dialog = builder.create()

        close_iv.setOnClickListener {
            dialog.cancel()
        }

        male_cv.setOnClickListener {
            gender = "1"
            binding.gender.setText("Male")
            dialog.cancel()
        }

        female_cv.setOnClickListener {
            gender = "2"
            binding.gender.setText("Female")
            dialog.cancel()
        }
        dialog.show()
    }


}