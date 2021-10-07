package com.hss.hssbanksystem.ui.view.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.hss.hssbanksystem.R
import com.hss.hssbanksystem.databinding.ActivityNoLoggedUserBinding

class NoLoggedUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_logged_user)
    }
}