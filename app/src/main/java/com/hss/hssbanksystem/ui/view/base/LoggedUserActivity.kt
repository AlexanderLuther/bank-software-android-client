package com.hss.hssbanksystem.ui.view.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hss.hssbanksystem.R

class LoggedUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_user)
    }


}