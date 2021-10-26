package com.hss.hssbanksystem.ui.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.model.ServiceModel
import com.hss.hssbanksystem.data.model.UserModel
import com.hss.hssbanksystem.data.repository.ServiceRepository
import com.hss.hssbanksystem.data.repository.UserRepository
import com.hss.hssbanksystem.ui.viewmodel.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ServiceRepository
): BaseViewModel(repository){

    private val _serviceModel : MutableLiveData<Resource<ServiceModel>> = MutableLiveData()
    val serviceModel: LiveData<Resource<ServiceModel>>
        get() = _serviceModel

    fun getServices() = viewModelScope.launch {
        _serviceModel.value = Resource.Loading
        _serviceModel.value = repository.getServices()
    }

}
