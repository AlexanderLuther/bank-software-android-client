package com.hss.hssbanksystem.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.android.material.snackbar.Snackbar
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.model.ErrorModel

fun<A: Activity> Activity.startNewActivity(activity: Class<A>){
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean){
    visibility = if(isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enable: Boolean){
    isEnabled = enable
}

fun View.snackbar(message: String, action: (() -> Unit)? = null){
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry"){
            it()
        }
    }
    snackbar.show()
}

fun Fragment.handleApiError(failure: Resource.Failure, retry: (() -> Unit)? = null){
    when{
        failure.isNetworkError -> requireView().snackbar("No hay conexion a internet.", retry)
        else -> {
            requireView().snackbar(jacksonObjectMapper().readValue<ErrorModel>(failure.errorBody?.string().toString()).error)
        }
    }
}

fun Fragment.hideKeyboard(activity: Activity?){
    val view = activity?.currentFocus
    if (view != null) {
        val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}