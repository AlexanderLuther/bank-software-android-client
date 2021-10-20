package com.hss.hssbanksystem.ui.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.model.UserModel
import com.hss.hssbanksystem.data.repository.UserRepository
import com.hss.hssbanksystem.ui.viewmodel.base.BaseViewModel
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: UserRepository
): BaseViewModel(repository){

    private val _userModel : MutableLiveData<Resource<UserModel>> = MutableLiveData()
    val userModel: LiveData<Resource<UserModel>>
        get() = _userModel

    fun getUser() = viewModelScope.launch {
        _userModel.value = Resource.Loading
        _userModel.value = repository.getUser()
    }

    fun updateData(address: String, phoneNumber: String, civilStatus: String, occupation: String) = viewModelScope.launch {
        _userModel.value = Resource.Loading
        _userModel.value = repository.updateData(address, phoneNumber, civilStatus, occupation)
    }

}