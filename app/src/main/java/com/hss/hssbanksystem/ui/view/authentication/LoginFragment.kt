package com.hss.hssbanksystem.ui.view.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hss.hssbanksystem.R
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.core.handleApiError
import com.hss.hssbanksystem.core.hideKeyboard
import com.hss.hssbanksystem.core.startNewActivity
import com.hss.hssbanksystem.core.visible
import com.hss.hssbanksystem.data.network.AuthenticationApi
import com.hss.hssbanksystem.data.repository.AuthenticationRepository
import com.hss.hssbanksystem.databinding.FragmentLoginBinding
import com.hss.hssbanksystem.ui.view.base.BaseFragment
import com.hss.hssbanksystem.ui.view.base.HomeActivity
import com.hss.hssbanksystem.ui.viewmodel.authentication.AuthenticationViewModel
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<AuthenticationViewModel, FragmentLoginBinding, AuthenticationRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Ocultar la barra de cargga
        binding.progressBar.visible(false)

        //Mostrar un error si el nombre de usuario es vacio
        binding.usernameLayout.editText?.addTextChangedListener {
            if(binding.usernameLayout.editText?.text.toString().trim().isEmpty()) binding.usernameLayout.error = getString(R.string.usernameRequired)
            else binding.usernameLayout.error = null
        }

        //Mostrar un error si la contraseña esta vacia
        binding.passwordLayout.editText?.addTextChangedListener {
            if(binding.passwordLayout.editText?.text.toString().trim().isEmpty()) binding.passwordLayout.error = getString(R.string.passwordRequired)
            else binding.passwordLayout.error = null
        }

        //Validar los datos ingresados y ejecutar la solicitud de inicio de sesion
        binding.loginButton.setOnClickListener {
            hideKeyboard(activity)
            val username = binding.usernameLayout.editText?.text.toString().trim()
            val password = binding.passwordLayout.editText?.text.toString().trim()
            if(validateData(username, password)){
                viewModel.login(username, password)
            }
        }

        //Cambiar hacia el fragment de Registro
        binding.registerButton.setOnClickListener {
            viewModel.clear()
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
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
     * Funcion que valida que los campos obligatorios no esten vacios
     */
    private fun validateData(username: String, password:String):Boolean {
        if(username.isEmpty()) binding.usernameLayout.error = getString(R.string.usernameRequired)
        if(password.isEmpty()) binding.passwordLayout.error = getString(R.string.passwordRequired)
        return username.isNotEmpty() && password.isNotEmpty()
    }

    /**
     * Funciones sobrecargadas por la herencia de la clase abstracta BaseFragment
     */
    override fun getViewModel() = AuthenticationViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getRepository(): AuthenticationRepository = AuthenticationRepository(retrofitHelper.buildApi(AuthenticationApi::class.java), dataStoreHelper)

}