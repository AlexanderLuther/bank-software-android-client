package com.hss.hssbanksystem.ui.view.request

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.hss.hssbanksystem.R
import com.hss.hssbanksystem.core.handleApiError
import com.hss.hssbanksystem.core.hideKeyboard
import com.hss.hssbanksystem.core.snackbar
import com.hss.hssbanksystem.core.visible
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.network.RequestApi
import com.hss.hssbanksystem.data.repository.RequestRepository
import com.hss.hssbanksystem.databinding.FragmentCreditCardRequestBinding
import com.hss.hssbanksystem.databinding.FragmentLoanRequestBinding
import com.hss.hssbanksystem.ui.view.base.BaseFragment
import com.hss.hssbanksystem.ui.viewmodel.request.RequestViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class CreditCardRequestFragment : BaseFragment<RequestViewModel, FragmentCreditCardRequestBinding, RequestRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Ocultar la barra de cargga
        binding.progressBar.visible(false)

        //Mostrar un error si la cantidad solicitada esta vacia
        binding.amountLayout.editText?.addTextChangedListener {
            if(binding.amountLayout.editText?.text.toString().trim().isEmpty()) binding.amountLayout.error = getString(R.string.loanAmountRequired)
            else binding.amountLayout.error = null
        }

        //Mostrar un error si los ingresos mensuales estan vacia
        binding.incomeLayout.editText?.addTextChangedListener {
            if(binding.incomeLayout.editText?.text.toString().trim().isEmpty()) binding.incomeLayout.error = getString(R.string.incomeRequired)
            else binding.incomeLayout.error = null
        }

        //Validar los datos ingresados y ejecutar la solicitud de tarjeta de credito
        binding.requestButton.setOnClickListener {
            hideKeyboard(activity)
            val amount = binding.amountLayout.editText?.text.toString().trim()
            val income = binding.incomeLayout.editText?.text.toString().trim()
            if(validateData(income, amount)){
                hideKeyboard(activity)
                viewModel.requestCreditCard(income.toDouble(), amount.toDouble())
            }
        }

        //Setear el patron observador
        viewModel.requestModel.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    requireView().snackbar(it.value.message)
                    findNavController().navigate(CreditCardRequestFragmentDirections.actionNavCreditCardRequestFragmentToNavHomeFragment())
                }
                is Resource.Failure -> handleApiError(it)
            }
        })

    }

    /**
     * Funcion que valida que los campos obligatorios no esten vacios
     */
    private fun validateData(income:String, amount: String):Boolean {
        if(amount.isEmpty()) binding.amountLayout.error = getString(R.string.loanAmountRequired)
        if(income.isEmpty()) binding.incomeLayout.error = getString(R.string.incomeRequired)
        return amount.isNotEmpty() && income.isNotEmpty()
    }

    override fun getViewModel() = RequestViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreditCardRequestBinding = FragmentCreditCardRequestBinding.inflate(inflater, container, false)

    override fun getRepository(): RequestRepository {
        val token = runBlocking { dataStoreHelper.authenticationToken.first() }
        return RequestRepository(retrofitHelper.buildApi(RequestApi::class.java, token))
    }
}