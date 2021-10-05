package com.hss.hssbanksystem.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hss.hssbanksystem.core.Resource
import com.hss.hssbanksystem.data.model.LoginModel
import com.hss.hssbanksystem.data.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository): ViewModel() {

    private val _loginModel : MutableLiveData<Resource<LoginModel>> = MutableLiveData()
    val loginModel: LiveData<Resource<LoginModel>>
        get() = _loginModel

    fun login(username:String, password:String) = viewModelScope.launch{
        _loginModel.value = repository.login(username, password)
    }
}