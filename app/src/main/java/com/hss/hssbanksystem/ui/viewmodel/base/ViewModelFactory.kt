package com.hss.hssbanksystem.ui.viewmodel.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hss.hssbanksystem.data.repository.*
import com.hss.hssbanksystem.ui.viewmodel.home.HomeViewModel
import com.hss.hssbanksystem.ui.viewmodel.authentication.AuthenticationViewModel
import com.hss.hssbanksystem.ui.viewmodel.request.RequestViewModel
import com.hss.hssbanksystem.ui.viewmodel.service.ServiceViewModel
import com.hss.hssbanksystem.ui.viewmodel.user.ProfileViewModel
import com.hss.hssbanksystem.ui.viewmodel.user.UpdateUserDataViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: BaseRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AuthenticationViewModel::class.java) -> AuthenticationViewModel(repository as AuthenticationRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository as ServiceRepository) as T
            modelClass.isAssignableFrom(UpdateUserDataViewModel::class.java) -> UpdateUserDataViewModel(repository as UserRepository) as T
            modelClass.isAssignableFrom(RequestViewModel::class.java) -> RequestViewModel(repository as RequestRepository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(repository as UserRepository) as T
            modelClass.isAssignableFrom(ServiceViewModel::class.java) -> ServiceViewModel(repository as ServiceRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }

}