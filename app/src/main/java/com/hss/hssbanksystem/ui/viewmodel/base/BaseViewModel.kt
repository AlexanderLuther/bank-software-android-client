package com.hss.hssbanksystem.ui.viewmodel.base

import androidx.lifecycle.ViewModel
import com.hss.hssbanksystem.data.network.AuthenticationApi
import com.hss.hssbanksystem.data.repository.BaseRepository

abstract class BaseViewModel(
    private val repository: BaseRepository
) : ViewModel(){

    suspend fun logout(api: AuthenticationApi) = repository.logout(api)

}