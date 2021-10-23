package com.hss.hssbanksystem.ui.view.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.hss.hssbanksystem.R
import com.hss.hssbanksystem.core.handleApiError
import com.hss.hssbanksystem.core.hideKeyboard
import com.hss.hssbanksystem.core.visible
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.network.UserApi
import com.hss.hssbanksystem.data.repository.UserRepository
import com.hss.hssbanksystem.databinding.FragmentHomeBinding
import com.hss.hssbanksystem.databinding.FragmentProfileBinding
import com.hss.hssbanksystem.ui.view.authentication.LoginFragmentDirections
import com.hss.hssbanksystem.ui.view.base.BaseFragment
import com.hss.hssbanksystem.ui.viewmodel.home.HomeViewModel
import com.hss.hssbanksystem.ui.viewmodel.user.ProfileViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.math.BigInteger
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding, UserRepository>() {

    private val outputDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Ocultar la barra de cargga
        binding.progressBar.visible(false)

        //Obtener los datos del usuario
        viewModel.getUser()

        //Cambiar a la vista de actualizacion de datos y enviar los datos del usuario
        binding.editProfileButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("address", binding.addressLayout.editText?.text.toString().trim())
            bundle.putString("phoneNumber", binding.phoneNumberLayout.editText?.text.toString().trim())
            bundle.putString("civilStatus", binding.civilStatusLayout.editText?.text.toString().trim())
            bundle.putString("occupation", binding.occupationLayout.editText?.text.toString().trim())
            parentFragmentManager.setFragmentResult("data", bundle)
            findNavController().navigate(ProfileFragmentDirections.actionNavProfileFragmentToEditProfileFragment())
        }

        //Cambiar a la vista de cambio de contraseña
        binding.editPasswordButton.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionNavProfileFragmentToUpdatePasswordFragment())
        }

        //Setear el patron observador
        viewModel.userModel.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    binding.nameLayout.editText?.setText(it.value.name)
                    binding.surNameLayout.editText?.setText(it.value.surname)
                    binding.cuiLayout.editText?.setText(it.value.cui.toString())
                    binding.addressLayout.editText?.setText(it.value.address)
                    binding.phoneNumberLayout.editText?.setText(it.value.phoneNumber.toString())
                    binding.birthDayLayout.editText?.setText(outputDateFormat.format(it.value.birthDay))
                    binding.genderLayout.editText?.setText(it.value.gender)
                    binding.civilStatusLayout.editText?.setText(it.value.civilStatus)
                    binding.occupationLayout.editText?.setText(it.value.ocupation)
                }
                is Resource.Failure -> handleApiError(it)
            }
        })
    }

    override fun getViewModel() = ProfileViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

    override fun getRepository(): UserRepository {
        val token = runBlocking { dataStoreHelper.authenticationToken.first() }
        return UserRepository(retrofitHelper.buildApi(UserApi::class.java, token))
    }

}