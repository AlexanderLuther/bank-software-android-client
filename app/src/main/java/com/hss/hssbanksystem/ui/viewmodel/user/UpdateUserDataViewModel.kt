package com.hss.hssbanksystem.ui.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.model.UserModel
import com.hss.hssbanksystem.data.repository.UserRepository
import com.hss.hssbanksystem.ui.viewmodel.base.BaseViewModel
import kotlinx.coroutines.launch

class UpdateUserDataViewModel(
    private val repository: UserRepository
): BaseViewModel(repository){

    private val _userModel : MutableLiveData<Resource<UserModel>> = MutableLiveData()
    val userModel: LiveData<Resource<UserModel>>
        get() = _userModel

    fun updatePassword(oldPassword:String, newPassword:String) = viewModelScope.launch{
        _userModel.value = Resource.Loading
        _userModel.value = repository.updatePassword(oldPassword, newPassword)
    }

    fun updateEmail(email: String) = viewModelScope.launch {
        _userModel.value = Resource.Loading
        _userModel.value = repository.updateEmail(email)
    }

}