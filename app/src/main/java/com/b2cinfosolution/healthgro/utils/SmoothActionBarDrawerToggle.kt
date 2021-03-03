package com.b2cinfosolution.healthgro.utils

import android.app.Activity
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout

/**
 * Created by Anand
 */
open class SmoothActionBarDrawerToggle(activity: Activity?, drawerLayout: DrawerLayout?, toolbar: Toolbar?, openDrawerContentDescRes: Int, closeDrawerContentDescRes: Int) : ActionBarDrawerToggle(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes) {
    private var runnable: Runnable? = null
    override fun onDrawerOpened(drawerView: View) {
        super.onDrawerOpened(drawerView)
        //        invalidateOptionsMenu();
    }

    override fun onDrawerClosed(view: View) {
        super.onDrawerClosed(view)
        //        invalidateOptionsMenu();
    }

    override fun onDrawerStateChanged(newState: Int) {
        super.onDrawerStateChanged(newState)
        if (runnable != null && newState == DrawerLayout.STATE_IDLE) {
            runnable!!.run()
            runnable = null
        }
    }

    fun runWhenIdle(runnable: Runnable?) {
        this.runnable = runnable
    }
}