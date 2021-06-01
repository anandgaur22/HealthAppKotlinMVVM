package com.b2cinfosolution.healthgro.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.b2cinfosolution.healthgro.R
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


object Utility {

    val FADE_DURATION = 1000

    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun statusBarColor(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.statusBarColor = activity.resources.getColor(
                R.color.colorPrimaryDark,
                activity.theme
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.statusBarColor = activity.resources.getColor(R.color.colorPrimaryDark)
        }
    }


    fun convertTo12Hour(time: String?): String? {
        try {
            val sdf = SimpleDateFormat("H:mm")
            val dateObj = sdf.parse(time)
            return SimpleDateFormat("hh:mm a").format(dateObj)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    fun convertTo24Hour(Time: String?): String {
        val f1: DateFormat = SimpleDateFormat("hh:mm a") //11:00 pm
        var d: Date? = null
        try {
            d = f1.parse(Time)
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        val f2: DateFormat = SimpleDateFormat("HH:mm")
        return f2.format(d)
    }

    /*date format */
    fun convertDateFormatStandard(pass_date: String?): String? {
        val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy")
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var date: Date? = null
        try {
            date = inputFormat.parse(pass_date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        var formattedDate: String? = null
        if (date != null) {
            formattedDate = outputFormat.format(date)
        }
        return formattedDate
    }

    fun closeKeyboard(appCompatActivity: AppCompatActivity, view: View) {
        try {
            val imm =
                appCompatActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getDiffYears(first: Date?, last: Date?): Int {
        val a = getCalendar(first)
        val b = getCalendar(last)
        var diff = b[Calendar.YEAR] - a[Calendar.YEAR]
        if (a[Calendar.MONTH] > b[Calendar.MONTH] ||
            a[Calendar.MONTH] == b[Calendar.MONTH] && a[Calendar.DATE] > b[Calendar.DATE]
        ) {
            diff--
        }
        return diff
    }

    fun getCalendar(date: Date?): Calendar {
        val cal = Calendar.getInstance(Locale.US)
        cal.time = date
        return cal
    }


    fun convertDateFormat3(pass_date: String?): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
        val outputFormat = SimpleDateFormat("dd MMM, yyyy")
        var date: Date? = null
        try {
            date = inputFormat.parse(pass_date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        var formattedDate: String? = null
        if (date != null) {
            formattedDate = outputFormat.format(date)
        }
        return formattedDate
    }

    fun convertDateFormat0(pass_date: String?): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat = SimpleDateFormat("dd MMM, yyyy")
        var date: Date? = null
        try {
            date = inputFormat.parse(pass_date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        var formattedDate: String? = null
        if (date != null) {
            formattedDate = outputFormat.format(date)
        }
        return formattedDate
    }


    fun convertDateFormat4(pass_date: String?): String? {
        val inputFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputFormat = SimpleDateFormat("dd MMM, yyyy")
        var date: Date? = null
        try {
            date = inputFormat.parse(pass_date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        var formattedDate: String? = null
        if (date != null) {
            formattedDate = outputFormat.format(date)
        }
        return formattedDate
    }


    fun hideKeyboard(context: Context) {
        try {
            (context as Activity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            if (context.currentFocus != null && context.currentFocus!!.windowToken != null) {
                (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    context.currentFocus!!.windowToken,
                    0
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setScaleAnimation(view: View) {
        val anim = ScaleAnimation(
            0.6f,
            1.0f,
            0.6f,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        anim.duration = java.lang.Long.valueOf(FADE_DURATION.toLong())
        view.startAnimation(anim)
    }

    fun Alert(msg: String?, context: Context?) {
        val builder1 = AlertDialog.Builder(context)
        builder1.setMessage(msg)
        builder1.setCancelable(true)
        builder1.setPositiveButton(
            "Ok"
        ) { dialog, id -> dialog.cancel() }
        builder1.setNegativeButton(
            "Cancel"
        ) { dialog, id -> dialog.cancel() }
        val alert11 = builder1.create()
        alert11.window!!.attributes.windowAnimations = R.style.animationdialog
        alert11.show()
    }

    fun AlertMsg(msg: String?, context: Context?) {
        val builder1 = AlertDialog.Builder(context)
        builder1.setMessage(msg)
        builder1.setCancelable(true)
        builder1.setPositiveButton(
            "Ok"
        ) { dialog, id -> dialog.cancel() }
        val alert11 = builder1.create()
        alert11.window!!.attributes.windowAnimations = R.style.animationdialog
        alert11.show()
    }


    fun AlertDialogtitel(title: String?, msg: String?, context: Context?) {
        val builder1 = AlertDialog.Builder(context)
        builder1.setTitle(title)
        builder1.setMessage(msg)
        builder1.setCancelable(true)
        builder1.setPositiveButton(
            "Ok"
        ) { dialog, id -> dialog.cancel() }
        builder1.setNegativeButton(
            "Cancel"
        ) { dialog, id -> dialog.cancel() }
        val alert11 = builder1.create()
        alert11.window!!.attributes.windowAnimations = R.style.animationdialog
        alert11.show()
    }


    fun customeToastGreenTop(msg: String?, context: Context?) {
        val inflater = LayoutInflater.from(context)
        val child: View = inflater.inflate(R.layout.custome_toast, null)
        val text = child.findViewById<View>(R.id.toast_tv) as TextView
        val cardView = child.findViewById<View>(R.id.custom_toast_container) as CardView
        text.text = msg
        text.setTextColor(ContextCompat.getColor(context!!, R.color.toast_txtColour_green))
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_green))
        val toast = Toast(context)
        toast.setGravity(Gravity.TOP, 0, 140)
        toast.duration = Toast.LENGTH_LONG
        toast.setView(child)
        toast.show()
    }


    fun customeToastRedTop(msg: String?, context: Context?) {
        val inflater = LayoutInflater.from(context)
        val child: View = inflater.inflate(R.layout.custome_toast, null)
        val text = child.findViewById<View>(R.id.toast_tv) as TextView
        val cardView = child.findViewById<View>(R.id.custom_toast_container) as CardView
        text.text = msg
        text.setTextColor(ContextCompat.getColor(context!!, R.color.toast_txtColour_red))
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_red))
        val toast = Toast(context)
        toast.setGravity(Gravity.TOP, 0, 140)
        toast.duration = Toast.LENGTH_LONG
        toast.setView(child)
        toast.show()
    }


    fun customeToastGreenBottom(msg: String?, context: Context?) {
        val inflater = LayoutInflater.from(context)
        val child: View = inflater.inflate(R.layout.custome_toast, null)
        val text = child.findViewById<View>(R.id.toast_tv) as TextView
        val cardView = child.findViewById<View>(R.id.custom_toast_container) as CardView
        text.text = msg
        text.setTextColor(ContextCompat.getColor(context!!, R.color.toast_txtColour_green))
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_green))
        val toast = Toast(context)
        toast.setGravity(Gravity.BOTTOM, 0, 140)
        toast.duration = Toast.LENGTH_LONG
        toast.setView(child)
        toast.show()
    }


    fun customeToastRedBottom(msg: String?, context: Context?) {
        val inflater = LayoutInflater.from(context)
        val child: View = inflater.inflate(R.layout.custome_toast, null)
        val text = child.findViewById<View>(R.id.toast_tv) as TextView
        val cardView = child.findViewById<View>(R.id.custom_toast_container) as CardView
        text.text = msg
        text.setTextColor(ContextCompat.getColor(context!!, R.color.toast_txtColour_red))
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_red))
        val toast = Toast(context)
        toast.setGravity(Gravity.BOTTOM, 0, 140)
        toast.duration = Toast.LENGTH_LONG
        toast.setView(child)
        toast.show()
    }


    fun strt_time_picker(context: Context?) {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
        val minute = mcurrentTime[Calendar.MINUTE]
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(context, { timePicker, selectedHour, selectedMinute ->
            val time = "$selectedHour:$selectedMinute"
            val fmt = SimpleDateFormat("HH:mm")
            var date: Date? = null
            try {
                date = fmt.parse(time)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val fmtOut = SimpleDateFormat("hh:mm aa")
            val formattedTime = fmtOut.format(date)

            //  strt_time_tv.setText(formattedTime);


            // strt_time_tv.setText( selectedHour + ":" + selectedMinute);
        }, hour, minute, false) //Yes 24 hour time
        mTimePicker.setTitle("Select Time")
        mTimePicker.show()
    }

    fun end_time_picker(context: Context?) {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
        val minute = mcurrentTime[Calendar.MINUTE]
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(context, { timePicker, selectedHour, selectedMinute ->
            val time = "$selectedHour:$selectedMinute"
            val fmt = SimpleDateFormat("HH:mm")
            var date: Date? = null
            try {
                date = fmt.parse(time)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val fmtOut = SimpleDateFormat("hh:mm aa")
            val formattedTime = fmtOut.format(date)

            //end_time_tv.setText(formattedTime);


            //  end_time_tv.setText( selectedHour + ":" + selectedMinute);
        }, hour, minute, false) //Yes 24 hour time
        mTimePicker.setTitle("Select Time")
        mTimePicker.show()
    }

}