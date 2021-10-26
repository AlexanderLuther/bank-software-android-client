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
import com.hss.hssbanksystem.databinding.FragmentBankAccountRequestBinding
import com.hss.hssbanksystem.databinding.FragmentCardCancellationRequestBinding
import com.hss.hssbanksystem.ui.view.base.BaseFragment
import com.hss.hssbanksystem.ui.viewmodel.request.RequestViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class CardCancellationRequestFragment : BaseFragment<RequestViewModel, FragmentCardCancellationRequestBinding, RequestRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Ocultar la barra de cargga
        binding.progressBar.visible(false)

        //Mostrar un error si el numero de tarjeta esta vacio
        binding.cardIdLayout.editText?.addTextChangedListener {
            if(binding.cardIdLayout.editText?.text.toString().trim().isEmpty()) binding.cardIdLayout.error = getString(R.string.carIdRequired)
            else binding.cardIdLayout.error = null
        }

        //Mostrar un error si el motivo esta vacio
        binding.causeLayout.editText?.addTextChangedListener {
            if(binding.causeLayout.editText?.text.toString().trim().isEmpty()) binding.causeLayout.error =getString(R.string.causeRequired)
            else binding.causeLayout.error = null
        }

        //Validar datos ingresados y ejecutar la solicitud de cancelacion de tarjeta
        binding.requestButton.setOnClickListener{
            val cardId = binding.cardIdLayout.editText?.text.toString().trim()
            val cause =  binding.causeLayout.editText?.text.toString().trim()
            val type = when {
                binding.radioButton1.isChecked ->  2
                binding.radioButton2.isChecked ->  1
                else -> 0
            }
            if(validateData(cardId, cause, type)){
                hideKeyboard(activity)
                viewModel.requestCardCancellation(cardId, type, cause)
            }
        }

        //Setear el patron observador
        viewModel.requestModel.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    requireView().snackbar(it.value.message)
                    findNavController().navigate(CardCancellationRequestFragmentDirections.actionNavCancellationCardRequestFragmentToNavHomeFragment())
                }
                is Resource.Failure -> handleApiError(it)
            }
        })

    }

    /**
     * Funcion que valida que los campos obligatorios no esten vacios
     */
    private fun validateData(carId:String, cause: String, type: Int):Boolean {
        if(carId.isEmpty()) binding.cardIdLayout.error = getString(R.string.carIdRequired)
        if(cause.isEmpty()) binding.causeLayout.error = getString(R.string.causeRequired)
        if(type == 0) requireView().snackbar("No se ha seleccionado ningun tipo de tarjeta.")
        return carId.isNotEmpty() && cause.isNotEmpty() && type!=0
    }

    override fun getViewModel() = RequestViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCardCancellationRequestBinding = FragmentCardCancellationRequestBinding.inflate(inflater, container, false)

    override fun getRepository(): RequestRepository {
        val token = runBlocking { dataStoreHelper.authenticationToken.first() }
        return RequestRepository(retrofitHelper.buildApi(RequestApi::class.java, token))
    }
}