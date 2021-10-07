package com.hss.hssbanksystem.ui.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.hss.hssbanksystem.data.network.UserApi
import com.hss.hssbanksystem.data.repository.UserRepository
import com.hss.hssbanksystem.databinding.FragmentHomeBinding
import com.hss.hssbanksystem.ui.view.base.BaseFragment
import com.hss.hssbanksystem.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, UserRepository>() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.button.setOnClickListener {
            logout()
        }
    }

    override fun getViewModel() = HomeViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getRepository(): UserRepository {
        val token = runBlocking { dataStoreHelper.authenticationToken.first() }
        return UserRepository(retrofitHelper.buildApi(UserApi::class.java, token))
    }

}