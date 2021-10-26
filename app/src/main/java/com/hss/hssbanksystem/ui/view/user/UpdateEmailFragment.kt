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
import com.hss.hssbanksystem.databinding.FragmentUpdateEmailBinding
import com.hss.hssbanksystem.ui.view.base.BaseFragment
import com.hss.hssbanksystem.ui.viewmodel.user.UpdateUserDataViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class UpdateEmailFragment : BaseFragment<UpdateUserDataViewModel, FragmentUpdateEmailBinding, UserRepository>()  {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Ocultar la barra de cargga
        binding.progressBar.visible(false)

        //Mostrar un error si el correo electronico esta  vacio
        binding.emailLayout.editText?.addTextChangedListener {
            if(binding.emailLayout.editText?.text.toString().trim().isEmpty()) binding.emailLayout.error = getString(R.string.emailRequired)
            else binding.emailLayout.error = null
        }

        //Validar los datos ingresados y ejecutar la solicitud de actualizacion de email
        binding.updateButton.setOnClickListener {
            hideKeyboard(activity)
            val email = binding.emailLayout.editText?.text.toString().trim()
            if(validateData(email)){
                viewModel.updateEmail(email)
            }
        }

        //Setear el patron observador
        viewModel.userModel.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Email modificada con exito.", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(UpdateEmailFragmentDirections.actionUpdateEmailFragmentToNavProfileFragment())
                }
                is Resource.Failure -> handleApiError(it)
            }
        })
    }

    /**
     * Funcion que valida que los campos obligatorios no esten vacios
     */
    private fun validateData(email: String):Boolean {
        if(email.isEmpty()) binding.emailLayout.error = getString(R.string.emailRequired)
        return email.isNotEmpty()
    }



    override fun getViewModel() = UpdateUserDataViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUpdateEmailBinding = FragmentUpdateEmailBinding.inflate(inflater, container, false)

    override fun getRepository(): UserRepository {
        val token = runBlocking { dataStoreHelper.authenticationToken.first() }
        return UserRepository(retrofitHelper.buildApi(UserApi::class.java, token))
    }
}

