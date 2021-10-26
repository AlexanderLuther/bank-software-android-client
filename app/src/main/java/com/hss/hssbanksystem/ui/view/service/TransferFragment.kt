package com.hss.hssbanksystem.ui.view.service

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hss.hssbanksystem.R
import com.hss.hssbanksystem.core.*
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.network.AuthenticationApi
import com.hss.hssbanksystem.data.network.RequestApi
import com.hss.hssbanksystem.data.network.ServiceApi
import com.hss.hssbanksystem.data.repository.AuthenticationRepository
import com.hss.hssbanksystem.data.repository.RequestRepository
import com.hss.hssbanksystem.data.repository.ServiceRepository
import com.hss.hssbanksystem.databinding.FragmentLoginBinding
import com.hss.hssbanksystem.databinding.FragmentTransferBinding
import com.hss.hssbanksystem.ui.view.authentication.LoginFragmentDirections
import com.hss.hssbanksystem.ui.view.base.BaseFragment
import com.hss.hssbanksystem.ui.view.base.HomeActivity
import com.hss.hssbanksystem.ui.viewmodel.authentication.AuthenticationViewModel
import com.hss.hssbanksystem.ui.viewmodel.request.RequestViewModel
import com.hss.hssbanksystem.ui.viewmodel.service.ServiceViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TransferFragment : BaseFragment<RequestViewModel, FragmentTransferBinding, RequestRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Ocultar la barra de cargga
        binding.progressBar.visible(false)

        setAccountData()

        //Mostrar un error si la cuenta destino esta vacia
        binding.destinationAccountLayout.editText?.addTextChangedListener {
            if(binding.destinationAccountLayout.editText?.text.toString().trim().isEmpty()) binding.destinationAccountLayout.error = getString(R.string.destinationAccountRequired)
            else binding.destinationAccountLayout.error = null
        }

        //Mostrar un error si la cantidad a transferir esta vacia
        binding.amountLayout.editText?.addTextChangedListener {
            if(binding.amountLayout.editText?.text.toString().trim().isEmpty()) binding.amountLayout.error = getString(R.string.transferAmountRequired)
            else binding.amountLayout.error = null
        }

        //Validar los datos ingresados y ejecutar la solicitud de inicio de sesion
        binding.transferButton.setOnClickListener {
            hideKeyboard(activity)
            val originAccount = binding.originAccountLayout.editText?.text.toString().trim()
            val destinatioAccount = binding.destinationAccountLayout.editText?.text.toString().trim()
            val amount = binding.amountLayout.editText?.text.toString().trim()
            if(validateData(originAccount, destinatioAccount, amount)){
                viewModel.requestTransfer(originAccount.toLong(), destinatioAccount.toLong(), amount.toDouble())
            }
        }

        //Setear el patron observador
        viewModel.requestModel.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    view?.snackbar(it.value.message)
                    if(it.value.message.contains("exito")){
                        findNavController().navigate(TransferFragmentDirections.actionTransferFragmentToNavHomeFragment())
                    }
                }
                is Resource.Failure -> handleApiError(it)
            }
        })
    }

    /**
     * Funcion que valida que los campos obligatorios no esten vacios
     */
    private fun validateData(originAccount: String, destinationAccount:String, amount: String):Boolean {
        if(originAccount.isEmpty()) binding.originAccountLayout.error = getString(R.string.originAccountRequired)
        if(destinationAccount.isEmpty()) binding.destinationAccountLayout.error = getString(R.string.destinationAccountRequired)
        if(amount.isEmpty()) binding.amountLayout.error = getString(R.string.transferAmountRequired)
        return originAccount.isNotEmpty() && destinationAccount.isNotEmpty() && amount.isNotEmpty()
    }

    private fun setAccountData(){
        parentFragmentManager.setFragmentResultListener("accountData", this, FragmentResultListener { requestKey, result ->
            binding.originAccountLayout.editText?.setText(result.getString("id"))
        })
    }

    /**
     * Funciones sobrecargadas por la herencia de la clase abstracta BaseFragment
     */
    override fun getViewModel() = RequestViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTransferBinding = FragmentTransferBinding.inflate(inflater, container, false)

    override fun getRepository(): RequestRepository {
        val token = runBlocking { dataStoreHelper.authenticationToken.first() }
        return RequestRepository(retrofitHelper.buildApi(RequestApi::class.java, token))
    }


}