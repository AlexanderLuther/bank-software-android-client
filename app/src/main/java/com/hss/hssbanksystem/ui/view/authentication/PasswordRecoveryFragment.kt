package com.hss.hssbanksystem.ui.view.authentication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.hss.hssbanksystem.R
import com.hss.hssbanksystem.core.*
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.network.AuthenticationApi
import com.hss.hssbanksystem.data.network.UserApi
import com.hss.hssbanksystem.data.repository.AuthenticationRepository
import com.hss.hssbanksystem.data.repository.UserRepository
import com.hss.hssbanksystem.databinding.FragmentHomeBinding
import com.hss.hssbanksystem.databinding.FragmentLoginBinding
import com.hss.hssbanksystem.databinding.FragmentPasswordRecoveryBinding
import com.hss.hssbanksystem.databinding.FragmentUpdatePasswordBinding
import com.hss.hssbanksystem.ui.view.base.BaseFragment
import com.hss.hssbanksystem.ui.view.base.HomeActivity
import com.hss.hssbanksystem.ui.view.base.NoLoggedUserActivity
import com.hss.hssbanksystem.ui.viewmodel.authentication.AuthenticationViewModel
import com.hss.hssbanksystem.ui.viewmodel.home.HomeViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PasswordRecoveryFragment : BaseFragment<AuthenticationViewModel, FragmentPasswordRecoveryBinding, AuthenticationRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Ocultar la barra de cargga
        binding.progressBar.visible(false)

        //Mostrar un error si el nombre de usuario es vacio
        binding.usernameLayout.editText?.addTextChangedListener {
            if(binding.usernameLayout.editText?.text.toString().trim().isEmpty()) binding.usernameLayout.error = getString(R.string.usernameRequired)
            else binding.usernameLayout.error = null
        }

        //Validar los datos ingresados y ejecutar la solicitud de inicio de sesion
        binding.recoveryPassword.setOnClickListener {
            hideKeyboard(activity)
            val username = binding.usernameLayout.editText?.text.toString().trim()
            if(validateData(username)){
                viewModel.recoverPassword(username)
            }
        }

        //Setear el patron observador
        viewModel.authenticationModel.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    requireView().snackbar(getString(R.string.successPasswordRecovery))
                    requireActivity().startNewActivity(NoLoggedUserActivity::class.java)
                }
                is Resource.Failure -> handleApiError(it)
            }
        })
    }

    /**
     * Funcion que valida que los campos obligatorios no esten vacios
     */
    private fun validateData(username: String):Boolean {
        if(username.isEmpty()) binding.usernameLayout.error = getString(R.string.usernameRequired)
        return username.isNotEmpty()
    }


    /**
     * Funciones sobrecargadas por la herencia de la clase abstracta BaseFragment
     */
    override fun getViewModel() = AuthenticationViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPasswordRecoveryBinding = FragmentPasswordRecoveryBinding.inflate(inflater, container, false)

    override fun getRepository(): AuthenticationRepository = AuthenticationRepository(retrofitHelper.buildApi(AuthenticationApi::class.java), dataStoreHelper)
}