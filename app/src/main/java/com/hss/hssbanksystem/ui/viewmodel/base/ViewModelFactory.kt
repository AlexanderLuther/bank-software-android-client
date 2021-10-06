package com.hss.hssbanksystem.ui.viewmodel.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hss.hssbanksystem.data.repository.BaseRepository
import com.hss.hssbanksystem.data.repository.LoginRepository
import com.hss.hssbanksystem.ui.viewmodel.LoginViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: BaseRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(repository as LoginRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }

}