package com.hss.hssbanksystem.ui.view.service

import android.os.Bundle
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
import com.hss.hssbanksystem.data.adapter.AccountAdapter
import com.hss.hssbanksystem.data.network.ServiceApi
import com.hss.hssbanksystem.data.repository.ServiceRepository
import com.hss.hssbanksystem.databinding.FragmentAccountBinding
import com.hss.hssbanksystem.ui.view.base.BaseFragment
import com.hss.hssbanksystem.ui.viewmodel.service.ServiceViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class AccountFragment : BaseFragment<ServiceViewModel, FragmentAccountBinding, ServiceRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setAccountData()
        hideProgressBar()
        setRecyclerView()
        setObserverPattern()
    }

    private fun setAccountData(){
        parentFragmentManager.setFragmentResultListener("serviceData", this, FragmentResultListener { requestKey, result ->
            binding.idTextView.text = "Numero: " + result.getString("id").toString()
            binding.balanceTextView.text = "Balance: Q" + result.getString("balance").toString()
            binding.typeTextView.text = result.getString("type").toString()
            if (result.getString("type").toString() == getString(R.string.savingAccount)) binding.titleImage.setImageResource(R.drawable.saving_account)
            else binding.titleImage.setImageResource(R.drawable.monetary_account)
            viewModel.getAccount(result.getString("id").toString())
        })
    }

    private fun hideProgressBar(){
        binding.progressBar.visible(false)
    }

    private fun setRecyclerView(){
        binding.movementsRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.movementsRecyclerView.setHasFixedSize(true)
    }

    private fun setObserverPattern(){
        viewModel.accountModel.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    //Setear el adapter del RecyclerView
                    binding.movementsRecyclerView.adapter = AccountAdapter(it.value.movements)
                }
                is Resource.Failure -> handleApiError(it)
            }
        })
    }

    override fun getViewModel() = ServiceViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAccountBinding = FragmentAccountBinding.inflate(inflater, container, false)

    override fun getRepository(): ServiceRepository {
        val token = runBlocking { dataStoreHelper.authenticationToken.first() }
        return ServiceRepository(retrofitHelper.buildApi(ServiceApi::class.java, token))
    }

}