package com.hss.hssbanksystem.ui.view.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hss.hssbanksystem.core.handleApiError
import com.hss.hssbanksystem.core.visible
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.adapter.CardAdapter
import com.hss.hssbanksystem.data.adapter.LoanAdapter
import com.hss.hssbanksystem.data.network.ServiceApi
import com.hss.hssbanksystem.data.repository.ServiceRepository
import com.hss.hssbanksystem.databinding.FragmentCreditCardBinding
import com.hss.hssbanksystem.ui.view.base.BaseFragment
import com.hss.hssbanksystem.ui.viewmodel.service.ServiceViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class CreditCardFragment : BaseFragment<ServiceViewModel, FragmentCreditCardBinding, ServiceRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setCreditCardData()
        hideProgressBar()
        setRecyclerView()
        setObserverPattern()
    }

    private fun setCreditCardData(){
        parentFragmentManager.setFragmentResultListener("serviceData", this, FragmentResultListener { requestKey, result ->
            binding.idTextView.text = "Numero: " + result.getString("id").toString()
            binding.balanceTextView.text = "Balance: Q" + result.getString("balance").toString()
            binding.typeTextView.text = result.getString("type").toString()
            viewModel.getCreditCard(result.getString("id").toString())
        })
    }

    private fun hideProgressBar(){
        binding.progressBar.visible(false)
    }

    private fun setRecyclerView(){
        binding.creditCardRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.creditCardRecyclerView.setHasFixedSize(true)
    }

    private fun setObserverPattern(){
        viewModel.creditCardModel.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    //Seterar los valores informativos de la tarjeta de credito
                    binding.creditLimitTextView.text ="Limite de credito: Q" + it.value.creditLimit
                    binding.cutOffDayTextView.text ="Fecha maxima de pago: " + it.value.cutoffDate
                    binding.interesRateTextView.text ="Interes :" + (it.value.interestRate.toDouble() * 100) + "%"
                    //Setear el adapter del RecyclerView
                    binding.creditCardRecyclerView.adapter = CardAdapter(it.value.payments)
                }
                is Resource.Failure -> handleApiError(it)
            }
        })
    }

    override fun getViewModel() = ServiceViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreditCardBinding = FragmentCreditCardBinding.inflate(inflater, container, false)

    override fun getRepository(): ServiceRepository {
        val token = runBlocking { dataStoreHelper.authenticationToken.first() }
        return ServiceRepository(retrofitHelper.buildApi(ServiceApi::class.java, token))
    }

}
