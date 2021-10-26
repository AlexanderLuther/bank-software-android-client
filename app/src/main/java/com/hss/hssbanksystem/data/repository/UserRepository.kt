package com.hss.hssbanksystem.data.repository

import com.hss.hssbanksystem.data.network.UserApi

class UserRepository(
    private val api: UserApi,
): BaseRepository() {

    suspend fun updatePassword(oldPassword:String, newPassword:String) = safeApiCall {
        api.updatePassword(oldPassword, newPassword)
    }

    suspend fun getUser() = safeApiCall{
        api.getUser()
    }

    suspend fun updateEmail(email: String) = safeApiCall {
        api.updateEmail(email)
    }
}