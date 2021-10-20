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

    suspend fun updateData(address: String, phoneNumber: String, civilStatus: String, occupation: String) = safeApiCall {
        api.updateData(address, phoneNumber, civilStatus, occupation)
    }
}