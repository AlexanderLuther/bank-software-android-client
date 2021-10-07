package com.hss.hssbanksystem.ui.viewmodel.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.model.AuthenticationModel
import com.hss.hssbanksystem.data.repository.AuthenticationRepository
import com.hss.hssbanksystem.ui.viewmodel.base.BaseViewModel
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val repository: AuthenticationRepository
    ): BaseViewModel(repository) {

    private val _authenticationModel : MutableLiveData<Resource<AuthenticationModel>> = MutableLiveData()
    val authenticationModel: LiveData<Resource<AuthenticationModel>>
        get() = _authenticationModel

    fun login(username:String, password:String) = viewModelScope.launch{
        _authenticationModel.value = Resource.Loading
        _authenticationModel.value = repository.login(username, password)
    }

    suspend fun saveUserData(token:String, username:String, userType: Int) {
        repository.saveUserData(token, username, userType)
    }
}