package com.hss.hssbanksystem.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.alpha
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.hss.hssbanksystem.R
import com.hss.hssbanksystem.core.DataStoreHelper
import com.hss.hssbanksystem.core.startNewActivity
import com.hss.hssbanksystem.ui.view.base.HomeActivity
import com.hss.hssbanksystem.ui.view.base.NoLoggedUserActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_HSSBankSystem)
        super.onCreate(savedInstanceState)

        val dataStoreHelper = DataStoreHelper(this)
        dataStoreHelper.authenticationToken.asLiveData().observe(this, Observer {
            val activity = if(it == null) NoLoggedUserActivity::class.java else HomeActivity::class.java
            startNewActivity(activity)
        })
    }
}