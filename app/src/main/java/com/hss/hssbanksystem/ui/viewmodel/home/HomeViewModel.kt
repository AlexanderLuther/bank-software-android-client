package com.hss.hssbanksystem.ui.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hss.hssbanksystem.data.Resource
import com.hss.hssbanksystem.data.model.UserModel
import com.hss.hssbanksystem.data.repository.UserRepository
import com.hss.hssbanksystem.ui.viewmodel.base.BaseViewModel

class HomeViewModel(
    private val repository: UserRepository
): BaseViewModel(repository){

    private val _userModel : MutableLiveData<Resource<UserModel>> = MutableLiveData()
    val userModel: LiveData<Resource<UserModel>>
        get() = _userModel

}
