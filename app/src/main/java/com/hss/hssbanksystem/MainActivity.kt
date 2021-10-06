package com.hss.hssbanksystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.hss.hssbanksystem.core.DataStoreHelper
import com.hss.hssbanksystem.ui.view.NoLoggedUserActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataStoreHelper = DataStoreHelper(this)
        dataStoreHelper.authenticationToken.asLiveData().observe(this, Observer {
            Toast.makeText(this, "Token is Null", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, NoLoggedUserActivity::class.java))
        })
    }
}