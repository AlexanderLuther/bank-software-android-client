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
import com.hss.hssbanksystem.data.adapter.LoanAdapter
import com.hss.hssbanksystem.data.network.ServiceApi
import com.hss.hssbanksystem.data.repository.ServiceRepository
import com.hss.hssbanksystem.databinding.FragmentLoanBinding
import com.hss.hssbanksystem.ui.view.base.BaseFragment
import com.hss.hssbanksystem.ui.viewmodel.service.ServiceViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class LoanFragment : BaseFragment<ServiceViewModel, FragmentLoanBinding, ServiceRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setLoanData()
        hideProgressBar()
        setRecyclerView()
        setObserverPattern()
    }

    private fun setLoanData(){
        parentFragmentManager.setFragmentResultListener("serviceData", this, FragmentResultListener { requestKey, result ->
            binding.idTextView.text = "Identificador: " + result.getString("id").toString()
            binding.balanceTextView.text = "Balance: Q" + result.getString("balance").toString()
            binding.typeTextView.text = result.getString("type").toString()
            viewModel.getLoan(result.getString("id").toString())
        })
    }

    private fun hideProgressBar(){
        binding.progressBar.visible(false)
    }

    private fun setRecyclerView(){
        binding.paymentsRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.paymentsRecyclerView.setHasFixedSize(true)
    }

    private fun setObserverPattern(){
        viewModel.loanModel.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    //Setear los valores del prestamo
                    binding.totalLoanTextView.text = "Cantidad Prestada : Q" + it.value.amount
                    binding.cutOffDateTextView.text = "Fecha limite para ejecutar pago: " + it.value.cutOffDate
                    binding.interesRateTextView.text = "Procentaje de interes: " + (it.value.interestRate.toDouble() * 100) + "%"
                    binding.monthlyPaymentTextView.text = "Pago mensual: Q" + it.value.monthlyPayment
                    binding.cuiGuarantorTextView.text = "CUI del fiador: " + it.value.guarantorCui
                    //Setear el adapter del RecyclerView
                    binding.paymentsRecyclerView.adapter = LoanAdapter(it.value.payments)
                }
                is Resource.Failure -> handleApiError(it)
            }
        })
    }

    override fun getViewModel() = ServiceViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoanBinding = FragmentLoanBinding.inflate(inflater, container, false)

    override fun getRepository(): ServiceRepository {
        val token = runBlocking { dataStoreHelper.authenticationToken.first() }
        return ServiceRepository(retrofitHelper.buildApi(ServiceApi::class.java, token))
    }

}