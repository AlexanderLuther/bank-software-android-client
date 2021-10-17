package com.hss.hssbanksystem.ui.viewmodel.request

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.model.RequestModel
import com.hss.hssbanksystem.data.repository.RequestRepository
import com.hss.hssbanksystem.ui.viewmodel.base.BaseViewModel
import kotlinx.coroutines.launch

class RequestViewModel(
    private val repository: RequestRepository
) : BaseViewModel(repository){

    private val _requestModel : MutableLiveData<Resource<RequestModel>> = MutableLiveData()
    val requestModel: LiveData<Resource<RequestModel>>
        get() = _requestModel

    fun requestBankAccount(type: Int) = viewModelScope.launch{
        _requestModel.value = Resource.Loading
        _requestModel.value = repository.requestBankAccount(type)
    }

    fun requestCreditCard(monthlyIncome: Double, creditAmount: Double) = viewModelScope.launch{

    }

    fun requestLoan(amount: Double, monthlyIncome: Double, cause:String, guarantorCui: String) = viewModelScope.launch{

    }
}