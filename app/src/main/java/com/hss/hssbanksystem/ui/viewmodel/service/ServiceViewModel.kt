package com.hss.hssbanksystem.ui.viewmodel.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.model.AccountModel
import com.hss.hssbanksystem.data.model.CreditCardModel
import com.hss.hssbanksystem.data.model.DebitCardModel
import com.hss.hssbanksystem.data.model.LoanModel
import com.hss.hssbanksystem.data.repository.ServiceRepository
import com.hss.hssbanksystem.ui.viewmodel.base.BaseViewModel
import kotlinx.coroutines.launch

class ServiceViewModel(
    private val repository: ServiceRepository
): BaseViewModel(repository){

    private val _accountModel : MutableLiveData<Resource<AccountModel>> = MutableLiveData()
    val accountModel: LiveData<Resource<AccountModel>>
        get() = _accountModel

    private val _loanModel : MutableLiveData<Resource<LoanModel>> = MutableLiveData()
    val loanModel: LiveData<Resource<LoanModel>>
        get() = _loanModel

    private val _creditCardModel : MutableLiveData<Resource<CreditCardModel>> = MutableLiveData()
    val creditCardModel: LiveData<Resource<CreditCardModel>>
        get() = _creditCardModel

    private val _debitCardModel : MutableLiveData<Resource<DebitCardModel>> = MutableLiveData()
    val debitCardModel: LiveData<Resource<DebitCardModel>>
        get() = _debitCardModel

    fun getAccount(id: String) = viewModelScope.launch {
        _accountModel.value = Resource.Loading
        _accountModel.value = repository.getAccount(id)
    }

    fun getLoan(id: String) = viewModelScope.launch {
        _loanModel.value = Resource.Loading
        _loanModel.value = repository.getLoan(id)
    }

    fun getCreditCard(id: String) = viewModelScope.launch {
        _creditCardModel.value = Resource.Loading
        _creditCardModel.value = repository.getCreditCard(id)
    }

    fun getDebitCard(id: String) = viewModelScope.launch {
        _debitCardModel.value = Resource.Loading
        _debitCardModel.value = repository.getDebitCard(id)
    }

}
