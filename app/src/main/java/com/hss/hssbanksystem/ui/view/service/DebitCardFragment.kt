package com.hss.hssbanksystem.ui.view.service

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hss.hssbanksystem.R
import com.hss.hssbanksystem.core.handleApiError
import com.hss.hssbanksystem.core.visible
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.adapter.CardAdapter
import com.hss.hssbanksystem.data.adapter.LoanAdapter
import com.hss.hssbanksystem.data.network.ServiceApi
import com.hss.hssbanksystem.data.repository.ServiceRepository
import com.hss.hssbanksystem.databinding.FragmentCreditCardBinding
import com.hss.hssbanksystem.databinding.FragmentDebitCardBinding
import com.hss.hssbanksystem.ui.view.base.BaseFragment
import com.hss.hssbanksystem.ui.viewmodel.service.ServiceViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class DebitCardFragment : BaseFragment<ServiceViewModel, FragmentDebitCardBinding, ServiceRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setAccountData()
        hideProgressBar()
        setRecyclerView()
        setObserverPattern()
    }

    private fun setAccountData(){
        parentFragmentManager.setFragmentResultListener("serviceData", this, FragmentResultListener { requestKey, result ->
            binding.idTextView.text = "Numerp: " + result.getString("id").toString()
            binding.balanceTextView.text = "Balance: Q" + result.getString("balance").toString()
            binding.typeTextView.text = result.getString("type").toString()
            viewModel.getDebitCard(result.getString("id").toString())
        })
    }

    private fun hideProgressBar(){
        binding.progressBar.visible(false)
    }

    private fun setRecyclerView(){
        binding.debitCardRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.debitCardRecyclerView.setHasFixedSize(true)
    }

    private fun setObserverPattern(){
        viewModel.debitCardModel.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    //Setear los valores informativos de la tarjeta de debito
                    binding.accountTextView.text = "Numero de cuenta enlazada: " + it.value.idAccount
                    //Setear el adapter del RecyclerView
                    binding.debitCardRecyclerView.adapter = CardAdapter(it.value.payments)
                }
                is Resource.Failure -> handleApiError(it)
            }
        })
    }

    override fun getViewModel() = ServiceViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDebitCardBinding = FragmentDebitCardBinding.inflate(inflater, container, false)

    override fun getRepository(): ServiceRepository {
        val token = runBlocking { dataStoreHelper.authenticationToken.first() }
        return ServiceRepository(retrofitHelper.buildApi(ServiceApi::class.java, token))
    }

}