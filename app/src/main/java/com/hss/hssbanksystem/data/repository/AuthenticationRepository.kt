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

    suspend fun saveUserData(token:String, username:String, userType: Int){
        dataStoreHelper.saveUserData(token, username, userType)
    }
}