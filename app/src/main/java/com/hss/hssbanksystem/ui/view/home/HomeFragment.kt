package com.hss.hssbanksystem.ui.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hss.hssbanksystem.R
import com.hss.hssbanksystem.core.handleApiError
import com.hss.hssbanksystem.core.visible
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.adapter.ServiceAdapter
import com.hss.hssbanksystem.data.model.ServiceModelItem
import com.hss.hssbanksystem.data.network.ServiceApi
import com.hss.hssbanksystem.data.repository.ServiceRepository
import com.hss.hssbanksystem.databinding.FragmentHomeBinding
import com.hss.hssbanksystem.ui.view.base.BaseFragment
import com.hss.hssbanksystem.ui.viewmodel.home.HomeViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, ServiceRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hideProgressBar()
        setRecyclerView()
        viewModel.getServices()
        setObserverPattern()
    }

    private fun hideProgressBar(){
        binding.progressBar.visible(false)
    }

    private fun setRecyclerView(){
        binding.servicesRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.servicesRecyclerView.setHasFixedSize(true)
    }

    private fun setObserverPattern(){
        viewModel.serviceModel.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    setServiceImage(it.value)
                    //Establecer el listener del adapter
                    val adapter = ServiceAdapter(it.value, object : ServiceAdapter.onButtonClickListener{
                        override fun onTransferButtonClick(position: Int) {
                            Toast.makeText(activity, "Transferencia" + it.value[position].toString(), Toast.LENGTH_SHORT).show()
                        }

                        override fun onViewButtonClick(position: Int) {
                            val bundle = Bundle()
                            bundle.putString("id", "id")
                            parentFragmentManager.setFragmentResult("serviceData", bundle)
                            val service = it.value[position]
                            when{
                                service.type == getString(R.string.savingAccount) -> findNavController().navigate(HomeFragmentDirections.actionNavHomeFragmentToAccountFragment())
                                service.type == getString(R.string.monetaryAccount) -> findNavController().navigate(HomeFragmentDirections.actionNavHomeFragmentToAccountFragment())
                                service.type == getString(R.string.creditCard2) -> findNavController().navigate(HomeFragmentDirections.actionNavHomeFragmentToCreditCardFragment())
                                service.type == getString(R.string.loan2) -> findNavController().navigate(HomeFragmentDirections.actionNavHomeFragmentToLoanFragment())
                            }
                        }
                    })

                    //Setear el adapter del RecyclerView
                    binding.servicesRecyclerView.adapter = adapter
                }
                is Resource.Failure -> handleApiError(it)
            }
        })
    }

    private fun setServiceImage(services: ArrayList<ServiceModelItem>){
        for(service in services){
            when {
                service.type == "Cuenta de ahorro" -> service.image = R.drawable.saving_account
                service.type == "Cuenta monetaria" -> service.image = R.drawable.monetary_account
                service.type == "Tarjeta de credito" -> service.image = R.drawable.credit_card
                service.type == "Tarjeta de debito" -> service.image = R.drawable.debit_card
                service.type == "Prestamo bancario" -> service.image = R.drawable.loan_home
            }
        }
    }

    override fun getViewModel() = HomeViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getRepository(): ServiceRepository {
        val token = runBlocking { dataStoreHelper.authenticationToken.first() }
        return ServiceRepository(retrofitHelper.buildApi(ServiceApi::class.java, token))
    }

}