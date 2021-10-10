package com.hss.hssbanksystem.ui.view.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.hss.hssbanksystem.R
import com.hss.hssbanksystem.core.handleApiError
import com.hss.hssbanksystem.core.hideKeyboard
import com.hss.hssbanksystem.core.startNewActivity
import com.hss.hssbanksystem.core.visible
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.network.AuthenticationApi
import com.hss.hssbanksystem.data.repository.AuthenticationRepository
import com.hss.hssbanksystem.databinding.FragmentRegisterBinding
import com.hss.hssbanksystem.ui.view.base.BaseFragment
import com.hss.hssbanksystem.ui.view.base.HomeActivity
import com.hss.hssbanksystem.ui.viewmodel.authentication.AuthenticationViewModel
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<AuthenticationViewModel, FragmentRegisterBinding, AuthenticationRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Ocultar la barra de cargga
        binding.progressBar.visible(false)

        //Mostrar un error si el cui esta vacion o es menos a 13 caracteres
        binding.cuiLayout.editText?.addTextChangedListener{
            when {
                binding.cuiLayout.editText?.text.toString().isEmpty() -> binding.cuiLayout.error = getString(R.string.cuiRequired)
                binding.cuiLayout.editText?.text?.length != 13 -> binding.cuiLayout.error = getString(R.string.noValidCui)
                else -> binding.cuiLayout.error = null
            }
        }

        //Mostrar un error si la el nombre de usuario esta vacio
        binding.usernameLayout.editText?.addTextChangedListener {
            if(binding.usernameLayout.editText?.text.toString().trim().isEmpty()) binding.usernameLayout.error = getString(R.string.usernameRequired)
            else binding.usernameLayout.error = null
        }

        //Mostrar un error si la contraseña esta vacia
        binding.passwordLayout.editText?.addTextChangedListener {
            if(binding.passwordLayout.editText?.text.toString().trim().isEmpty()) binding.passwordLayout.error = getString(R.string.passwordRequired)
            else binding.passwordLayout.error = null
        }

        //Validar los campos y procesar la solicitud de registro de nuevo usuario
        binding.registerButton.setOnClickListener {
            hideKeyboard(activity)
            val username = binding.usernameLayout.editText?.text.toString().trim()
            val password = binding.passwordLayout.editText?.text.toString().trim()
            val cui = binding.cuiLayout.editText?.text.toString().trim()
            if(validateData(username, password, cui)){
                viewModel.createUser(username, password, 1, cui)
            }
        }

        //Setear el patron observador
        viewModel.authenticationModel.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch{
                        viewModel.saveUserData(it.value.token, it.value.username)
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it)
            }
        })
    }

    /**
     * Funcion que valida que los campos obligatorios no esten vacios, de estarlo muetra el error
     */
    private fun validateData(username: String, password:String, cui:String):Boolean {
        if(username.isEmpty()) binding.usernameLayout.error = getString(R.string.usernameRequired)
        if(password.isEmpty()) binding.passwordLayout.error = getString(R.string.passwordRequired)
        if(cui.isEmpty()) binding.cuiLayout.error = getString(R.string.cuiRequired)
        return username.isNotEmpty() && password.isNotEmpty() && cui.isNotEmpty()
    }

    /**
     * Funciones sobrecargadas por la herencia de la clase abstracta BaseFragment
     */
    override fun getViewModel() = AuthenticationViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)

    override fun getRepository(): AuthenticationRepository = AuthenticationRepository(retrofitHelper.buildApi(AuthenticationApi::class.java), dataStoreHelper)

}