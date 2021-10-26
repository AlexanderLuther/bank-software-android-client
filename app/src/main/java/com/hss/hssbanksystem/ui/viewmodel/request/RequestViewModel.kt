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
        _requestModel.value = Resource.Loading
        _requestModel.value = repository.requestCreditCard(monthlyIncome, creditAmount)
    }

    fun requestDebitCard(id: String) = viewModelScope.launch {
        _requestModel.value = Resource.Loading
        _requestModel.value = repository.requestDebitCard(id)
    }

    fun requestLoan(amount: Double, monthlyIncome: Double, cause:String, guarantorCui: String) = viewModelScope.launch{
        _requestModel.value = Resource.Loading
        _requestModel.value = repository.requestLoan(amount, monthlyIncome, cause, guarantorCui)
    }

    fun requestCardCancellation(idCard: String, cardType: Int, cause: String) = viewModelScope.launch{
        _requestModel.value = Resource.Loading
        _requestModel.value = repository.requestCardCancellation(idCard, cardType, cause)
    }

    fun requestUpdateData(address: String, phoneNumber: Long, civilStatus: String, occupation: String) = viewModelScope.launch {
        _requestModel.value = Resource.Loading
        _requestModel.value = repository.requestUpdateData(address, phoneNumber, civilStatus, occupation)
    }

    fun requestTransfer(originAccount: Long, destinatioAccount: Long, amount: Double) = viewModelScope.launch {
        _requestModel.value = Resource.Loading
        _requestModel.value = repository.requestTransfer(originAccount, destinatioAccount, amount)
    }

}