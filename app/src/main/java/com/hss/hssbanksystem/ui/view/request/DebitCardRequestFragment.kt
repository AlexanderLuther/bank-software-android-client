package com.hss.hssbanksystem.ui.view.request

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hss.hssbanksystem.R
import com.hss.hssbanksystem.core.*
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.network.RequestApi
import com.hss.hssbanksystem.data.repository.RequestRepository
import com.hss.hssbanksystem.databinding.FragmentBankAccountRequestBinding
import com.hss.hssbanksystem.databinding.FragmentDebitCardRequestBinding
import com.hss.hssbanksystem.ui.view.base.BaseFragment
import com.hss.hssbanksystem.ui.view.base.HomeActivity
import com.hss.hssbanksystem.ui.viewmodel.request.RequestViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.reflect.Array

class DebitCardRequestFragment : BaseFragment<RequestViewModel, FragmentDebitCardRequestBinding, RequestRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Ocultar la barra de cargga
        binding.progressBar.visible(false)


        //Mostrar un error si el numero de cuenta de ahorro es vacio
        binding.accountIdLayout.editText?.addTextChangedListener {
            if(binding.accountIdLayout.editText?.text.toString().trim().isEmpty()) binding.accountIdLayout.error = getString(R.string.savingAccountIdRequired)
            else binding.accountIdLayout.error = null
        }

        //Validar datos ingresados y ejecutar la solicitud de tarjeta de debito
        binding.requestButton.setOnClickListener{
            val id = binding.accountIdLayout.editText?.text.toString().trim()
            if(validateData(id)) {
                hideKeyboard(activity)
                viewModel.requestDebitCard(id)
            }
        }

        //Setear el patron observador sobre el modelo de solicitudes
        viewModel.requestModel.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    requireView().snackbar(it.value.message)
                    findNavController().navigate(DebitCardRequestFragmentDirections.actionNavDebitCardRequestFragmentToNavHomeFragment())
                }
                is Resource.Failure -> handleApiError(it)
            }
        })

    }

    /**
     * Funcion que valida que los campos obligatorios no esten vacios
     */
    private fun validateData(accountId: String):Boolean {
        if(accountId.isEmpty()) binding.accountIdLayout.error = getString(R.string.savingAccountIdRequired)
        return accountId.isNotEmpty()
    }

    override fun getViewModel() = RequestViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDebitCardRequestBinding = FragmentDebitCardRequestBinding.inflate(inflater, container, false)

    override fun getRepository(): RequestRepository {
        val token = runBlocking { dataStoreHelper.authenticationToken.first() }
        return RequestRepository(retrofitHelper.buildApi(RequestApi::class.java, token))
    }
}