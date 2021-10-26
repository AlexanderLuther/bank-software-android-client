package com.hss.hssbanksystem.data.repository

import com.hss.hssbanksystem.core.DataStoreHelper
import com.hss.hssbanksystem.data.network.AuthenticationApi

class AuthenticationRepository(
    private val api: AuthenticationApi,
    private val dataStoreHelper: DataStoreHelper
) : BaseRepository() {

    suspend  fun login(username: String, password: String) = safeApiCall{
        api.login(username, password)
    }

    suspend fun createUser(username: String, password: String, userType: Int, cui: String, email: String) = safeApiCall{
        api.createUser(username, password, userType, cui, email)
    }

    suspend fun recoverPassword(username: String) = safeApiCall{
        api.recoverPassword(username)
    }
    suspend fun saveUserData(token:String, username:String){
        dataStoreHelper.saveUserData(token, username)
    }
}