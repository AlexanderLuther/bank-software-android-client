package com.hss.hssbanksystem.ui.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.model.RequestModel
import com.hss.hssbanksystem.data.model.UserModel
import com.hss.hssbanksystem.data.repository.UserRepository
import com.hss.hssbanksystem.ui.viewmodel.base.BaseViewModel
import kotlinx.coroutines.launch
import retrofit2.Call

class ProfileViewModel(
    private val repository: UserRepository
): BaseViewModel(repository){

    private val _userModel : MutableLiveData<Resource<UserModel>> = MutableLiveData()
    val userModel: LiveData<Resource<UserModel>>
        get() = _userModel

    private val _requestModel : MutableLiveData<Resource<RequestModel>> = MutableLiveData()
    val requestModel: LiveData<Resource<RequestModel>>
        get() = requestModel

    fun getUser() = viewModelScope.launch {
        _userModel.value = Resource.Loading
        _userModel.value = repository.getUser()
    }

}