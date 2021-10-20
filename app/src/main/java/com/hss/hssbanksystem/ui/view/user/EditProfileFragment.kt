package com.hss.hssbanksystem.ui.view.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.datastore.preferences.protobuf.Empty
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.hss.hssbanksystem.R
import com.hss.hssbanksystem.core.handleApiError
import com.hss.hssbanksystem.core.snackbar
import com.hss.hssbanksystem.core.visible
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.network.UserApi
import com.hss.hssbanksystem.data.repository.UserRepository
import com.hss.hssbanksystem.databinding.FragmentEditProfileBinding
import com.hss.hssbanksystem.databinding.FragmentProfileBinding
import com.hss.hssbanksystem.ui.view.base.BaseFragment
import com.hss.hssbanksystem.ui.viewmodel.user.ProfileViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class EditProfileFragment  : BaseFragment<ProfileViewModel, FragmentEditProfileBinding, UserRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Setear los datos actuales del usuario
        parentFragmentManager.setFragmentResultListener("data", this, FragmentResultListener { requestKey, result ->
            binding.addressLayoutEditProfile.editText?.setText(result.getString("address"))
            binding.phoneNumberLayout.editText?.setText(result.getString("phoneNumber"))
            binding.civilStatusLayout.editText?.setText(result.getString("civilStatus"))
            binding.occupationLayout.editText?.setText(result.getString("occupation"))

        })

        //Ocultar la barra de cargga
        binding.progressBar.visible(false)

        //Mostrar un error si la direccion esta vacia
        binding.addressLayoutEditProfile.editText?.addTextChangedListener {
            if(binding.addressLayoutEditProfile.editText?.text.toString().trim().isEmpty()) binding.addressLayoutEditProfile.error = getString(R.string.addressRequired)
            else binding.addressLayoutEditProfile.error = null
        }

        //Mostrar un error si el numero de telefono esta vacio o es menos a 8 caracteres
        binding.phoneNumberLayout.editText?.addTextChangedListener{
            when {
                binding.phoneNumberLayout.editText?.text.toString().isEmpty() -> binding.phoneNumberLayout.error = getString(R.string.phoneNumberRequired)
                binding.phoneNumberLayout.editText?.text?.length != 8 -> binding.phoneNumberLayout.error = getString(R.string.noValidPhoneNumber)
                else -> binding.phoneNumberLayout.error = null
            }
        }

        //Mostrar un error si el estado civiil esta vacio
        binding.civilStatusLayout.editText?.addTextChangedListener {
            if(binding.civilStatusLayout.editText?.text.toString().trim().isEmpty()) binding.civilStatusLayout.error = getString(R.string.civilStatusRequired)
            else binding.civilStatusLayout.error = null
        }

        //Mostrar un error si la la ocupacion esta vacia
        binding.occupationLayout.editText?.addTextChangedListener {
            if(binding.occupationLayout.editText?.text.toString().trim().isEmpty()) binding.occupationLayout.error = getString(R.string.occupationRequired)
            else binding.occupationLayout.error = null
        }

        binding.updateButton.setOnClickListener {
            val address = binding.addressLayoutEditProfile.editText?.text.toString().trim()
            val phoneNumber = binding.phoneNumberLayout.editText?.text.toString().trim()
            val civilStatus = binding.civilStatusLayout.editText?.text.toString().trim()
            val occupation = binding.occupationLayout.editText?.text.toString().trim()
            if(validateData(address, phoneNumber, civilStatus, occupation)){
                viewModel.updateData(address, phoneNumber, civilStatus, occupation)
            }
        }

        //Setear el patron observador
        viewModel.userModel.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    requireView().snackbar(getString(R.string.successDataUpdate))
                    findNavController().navigate(EditProfileFragmentDirections.actionEditProfileFragmentToNavProfileFragment())
                }
                is Resource.Failure -> handleApiError(it)
            }
        })
    }

    /**
     * Funcion que valida que los campos obligatorios no esten vacios, de estarlo muestra el error
     */
    private fun validateData(address: String, phoneNumber:String, civilStatus:String, occupation: String):Boolean {
        if(address.isEmpty()) binding.addressLayoutEditProfile.error = getString(R.string.addressRequired)
        if(phoneNumber.isEmpty()) binding.phoneNumberLayout.error = getString(R.string.phoneNumberRequired)
        if(civilStatus.isEmpty()) binding.civilStatusLayout.error = getString(R.string.civilStatusRequired)
        if(occupation.isEmpty()) binding.occupationLayout.error = getString(R.string.occupationRequired)
        return address.isNotEmpty() && phoneNumber.isNotEmpty() && civilStatus.isNotEmpty() && occupation.isNotEmpty()
    }

    override fun getViewModel() = ProfileViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditProfileBinding = FragmentEditProfileBinding.inflate(inflater, container, false)

    override fun getRepository(): UserRepository {
        val token = runBlocking { dataStoreHelper.authenticationToken.first() }
        return UserRepository(retrofitHelper.buildApi(UserApi::class.java, token))
    }
}