package com.hss.hssbanksystem.ui.view.request

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
import com.hss.hssbanksystem.core.handleApiError
import com.hss.hssbanksystem.core.snackbar
import com.hss.hssbanksystem.core.startNewActivity
import com.hss.hssbanksystem.core.visible
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.model.RequestModel
import com.hss.hssbanksystem.data.network.RequestApi
import com.hss.hssbanksystem.data.network.UserApi
import com.hss.hssbanksystem.data.repository.RequestRepository
import com.hss.hssbanksystem.data.repository.UserRepository
import com.hss.hssbanksystem.databinding.FragmentBankAccountRequestBinding
import com.hss.hssbanksystem.databinding.FragmentHomeBinding
import com.hss.hssbanksystem.ui.view.authentication.LoginFragmentDirections
import com.hss.hssbanksystem.ui.view.base.BaseFragment
import com.hss.hssbanksystem.ui.view.base.HomeActivity
import com.hss.hssbanksystem.ui.viewmodel.home.HomeViewModel
import com.hss.hssbanksystem.ui.viewmodel.request.RequestViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class BankAccountRequestFragment : BaseFragment<RequestViewModel, FragmentBankAccountRequestBinding, RequestRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Ocultar la barra de cargga
        binding.progressBar.visible(false)

        //Validar datos ingresados y ejecutar la solicitud
        binding.requestButton.setOnClickListener{
            if(!binding.radioButton1.isChecked && !binding.radioButton2.isChecked){
                requireView().snackbar("No se ha seleccionado ningun tipo de cuenta.")
            } else{
                viewModel.requestBankAccount(getType())
            }
        }

        //Setear el patron observador
        viewModel.requestModel.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    requireView().snackbar(it.value.message)
                    findNavController().navigate(BankAccountRequestFragmentDirections.actionNavBankAccountRequestFragmentToNavHomeFragment())
                }
                is Resource.Failure -> handleApiError(it)
            }
        })

    }

    /**
     * Funcion que retorna un entero en base al tipo de cuenta que
     * el usuario ha seleccionado.
     */
    fun getType(): Int{
        return if(binding.radioButton1.isChecked) 1
        else 2
    }
    override fun getViewModel() = RequestViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBankAccountRequestBinding = FragmentBankAccountRequestBinding.inflate(inflater, container, false)

    override fun getRepository(): RequestRepository {
        val token = runBlocking { dataStoreHelper.authenticationToken.first() }
        return RequestRepository(retrofitHelper.buildApi(RequestApi::class.java, token))
    }

}