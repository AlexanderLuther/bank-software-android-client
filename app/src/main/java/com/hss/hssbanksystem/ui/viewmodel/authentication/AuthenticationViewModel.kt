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

    /**
     * Funcion que realiza el llamado a la funcion login del repositorio AuthenticationRepository
     * @param username Nombre de usuario del usuario a iniciar sesion
     * @param password Del usuario a iniciar sesion
     */
    fun login(username:String, password:String) = viewModelScope.launch{
        _authenticationModel.value = Resource.Loading
        _authenticationModel.value = repository.login(username, password)
    }

    /**
     * Funcion que realiza el llamado a la funcion createUser del repositorio AuthenticationRepository
     * @param username Nombre de usuario del usuario a crear
     * @param password Contrasena del usuario a crear
     * @param userType Tipo de usuario, siempre es el valor 1
     * @param cui Cui del usuario a crear
     */
    fun createUser(username: String, password: String, userType: Int, cui:String) = viewModelScope.launch {
        _authenticationModel.value = Resource.Loading
        _authenticationModel.value = repository.createUser(username, password, userType, cui)
    }

    /**
     * Funcion que elimina todos los datos de la variable _authenticationModel
     */
    fun clear(){
        _authenticationModel.value = null
    }

    /**
     * Funcion que llama funcion saveUserData del repositorio AuthenticationRepository
     * @param token Token a almacenar
     * @param username Nombre de usuario a almacenar
     */
    suspend fun saveUserData(token:String, username:String) {
        repository.saveUserData(token, username)
    }
}