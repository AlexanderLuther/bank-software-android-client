package com.hss.hssbanksystem.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.ui.view.base.BaseFragment
import org.json.JSONObject
import org.json.JSONTokener
import java.lang.reflect.InvocationTargetException

/**
 * Funcion que muestra en pantalla una nueva actividad
 * @param activity Activity a mostrar
 */
fun<A: Activity> Activity.startNewActivity(activity: Class<A>){
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

/**
 * Funcion que muestra u oculta un elemento  grafico
 * @param isVisible Booleano que indica el estado
 */
fun View.visible(isVisible: Boolean){
    visibility = if(isVisible) View.VISIBLE else View.GONE
}

/**
 * Funcion que habilita o deshabilita un elemento grafico
 * @param enable Booleano que indica el estado
 */
fun View.enable(enable: Boolean){
    isEnabled = enable
}

/**
 * Funcion que setea el contenido de una snackbar y la muestra en pontalla
 * @param message Contenido a mostrar en la snackbar
 * @param action Accion personalizada a realizar en la snackbar
 */
fun View.snackbar(message: String, action: (() -> Unit)? = null){
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry"){
            it()
        }
    }
    snackbar.show()
}

/**
 * Funcion que realiza el manejo de errores provenientes de la
 * comunicacion con el servidor remoto.
 * @param failure Response que contiene el error proveido por el servidor
 * @param action Accion personalizada a realizar en la snackbar
 */
fun Fragment.handleApiError(failure: Resource.Failure, retry: (() -> Unit)? = null){
    when{
        failure.isNetworkError -> requireView().snackbar("No se pudo establecer conexion con el servidor.", retry)
        failure.errorCode == 401 -> (this as BaseFragment<*,*,*>).logout()
        else -> {
            if(failure != null){
                val error = JSONTokener(failure.errorBody?.string().toString()).nextValue() as JSONObject
                requireView().snackbar(error.getString("information_message"))
            }
        }
    }

}

/**
 * Funcion que oculta el teclado
 * @param activity Activity donde se desea ocultar el teclado
 */
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