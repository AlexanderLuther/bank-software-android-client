package com.hss.hssbanksystem.ui.view.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.hss.hssbanksystem.R
import com.hss.hssbanksystem.core.handleApiError
import com.hss.hssbanksystem.core.hideKeyboard
import com.hss.hssbanksystem.core.visible
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.network.UserApi
import com.hss.hssbanksystem.data.repository.UserRepository
import com.hss.hssbanksystem.databinding.FragmentUpdatePasswordBinding
import com.hss.hssbanksystem.ui.view.base.BaseFragment
import com.hss.hssbanksystem.ui.viewmodel.user.UpdateUserDataViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class UpdatePasswordFragment :  BaseFragment<UpdateUserDataViewModel, FragmentUpdatePasswordBinding, UserRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Ocultar la barra de cargga
        binding.progressBar.visible(false)

        //Mostrar un error si la contraseña actual esta vacia
        binding.oldPasswordLayout.editText?.addTextChangedListener {
            if(binding.oldPasswordLayout.editText?.text.toString().trim().isEmpty()) binding.oldPasswordLayout.error = getString(R.string.oldPasswordRequired)
            else binding.oldPasswordLayout.error = null
        }

        //Mostrar un error si la nueva  contraseña esta vacia
        binding.newPasswordLayout.editText?.addTextChangedListener {
            if(binding.newPasswordLayout.editText?.text.toString().trim().isEmpty()) binding.newPasswordLayout.error = getString(R.string.newPasswordRequired)
            else binding.newPasswordLayout.error = null
        }

        //Validar los datos ingresados y ejecutar la solicitud de actualizacion de contraseña
        binding.updateButton.setOnClickListener {
            hideKeyboard(activity)
            val oldPassword = binding.oldPasswordLayout.editText?.text.toString().trim()
            val newPassword = binding.newPasswordLayout.editText?.text.toString().trim()
            if(validateData(oldPassword, newPassword)){
                viewModel.updatePassword(oldPassword, newPassword)
            }
        }

        //Setear el patron observador
        viewModel.userModel.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Contraseña modificada con exito.", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(UpdatePasswordFragmentDirections.actionUpdatePasswordFragmentToNavProfileFragment())
                }
                is Resource.Failure -> handleApiError(it)
            }
        })
    }

    /**
     * Funcion que valida que los campos obligatorios no esten vacios
     */
    private fun validateData(oldPassword: String, newPassword:String):Boolean {
        if(oldPassword.isEmpty()) binding.oldPasswordLayout.error = getString(R.string.oldPasswordRequired)
        if(newPassword.isEmpty()) binding.newPasswordLayout.error = getString(R.string.newPasswordRequired)
        return oldPassword.isNotEmpty() && newPassword.isNotEmpty()
    }

    override fun getViewModel() = UpdateUserDataViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUpdatePasswordBinding = FragmentUpdatePasswordBinding.inflate(inflater, container, false)

    override fun getRepository(): UserRepository {
        val token = runBlocking { dataStoreHelper.authenticationToken.first() }
        return UserRepository(retrofitHelper.buildApi(UserApi::class.java, token))
    }
}