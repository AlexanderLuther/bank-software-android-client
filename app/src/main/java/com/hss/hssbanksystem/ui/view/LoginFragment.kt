package com.hss.hssbanksystem.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.hss.hssbanksystem.core.Resource
import com.hss.hssbanksystem.data.network.LoginApi
import com.hss.hssbanksystem.data.repository.LoginRepository
import com.hss.hssbanksystem.databinding.FragmentLoginBinding
import com.hss.hssbanksystem.ui.view.base.BaseFragment
import com.hss.hssbanksystem.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding, LoginRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.loginButton.setOnClickListener {
            Toast.makeText(this.context, "Presionado", Toast.LENGTH_SHORT).show()
            val username = binding.usernameLayout.editText?.text.toString().trim()
            val password = binding.passwordLayout.editText?.text.toString().trim()
            viewModel.login(username, password)
        }

        viewModel.loginModel.observe(viewLifecycleOwner, Observer {
            when (it) {
                 is Resource.Success -> {
                     lifecycleScope.launch {
                         dataStoreHelper.saveUserData(it.value.token, it.value.username, it.value.userType)
                     }
                 }
                 is Resource.Failure -> {
                     Toast.makeText(requireContext(), "Login Failure", Toast.LENGTH_SHORT).show()
                 }
             }
        })

    }

    override fun getViewModel() = LoginViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getRepository(): LoginRepository = LoginRepository(retrofitHelper.buildApi(LoginApi::class.java))

}