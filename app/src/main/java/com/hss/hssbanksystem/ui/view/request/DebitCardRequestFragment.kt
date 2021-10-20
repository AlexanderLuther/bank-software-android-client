package com.hss.hssbanksystem.ui.view.request

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    private var defaultPosition = -1
    private var options = arrayOf<String>()
    private lateinit var builderSingle : AlertDialog.Builder

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Ocultar la barra de cargga
        binding.progressBar.visible(false)

        //Setear los valores iniciales del selector de cuentas
        setSelector()

        //Solicitar todas las cuentas que pueden ser vinculadas a una tarjeta de debito y mostralas en el selector
        binding.selectAccountButton.setOnClickListener {
            viewModel.getAccountsAvailableForDebitCard()
            setSelectorItems()
            builderSingle.show()
        }

        //Validar datos ingresados y ejecutar la solicitud de tarjeta de debito
        binding.requestButton.setOnClickListener{
            val id = binding.selectedAccountTextView.text.toString().trim()
            if(id.isEmpty()){
                requireView().snackbar(getString(R.string.noAccountSelected))
            } else{
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

        //Setear el patron observador sobre el modelo de id de cuentas
        viewModel.accountIdModel.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    options = it.value.toArray() as kotlin.Array<String>
                }
                is Resource.Failure -> handleApiError(it)
            }
        })

    }

    /**
     * Funcion que setea los parametros iniciales del AlertDialog
     */
    private fun setSelector(){
        builderSingle = AlertDialog.Builder(requireContext())
        builderSingle.setTitle(getString(R.string.selectAccount))
        builderSingle.setPositiveButton("Seleccionar") { dialog, _ ->  dialog.dismiss() }
    }

    private fun setSelectorItems(){
        builderSingle.setSingleChoiceItems(options, -1) { dialog, which ->
            defaultPosition = which
            binding.selectedAccountTextView.text = options[which]
        }
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