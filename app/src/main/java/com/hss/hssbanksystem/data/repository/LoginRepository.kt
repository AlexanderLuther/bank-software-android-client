package com.hss.hssbanksystem.data.repository

import com.hss.hssbanksystem.data.network.LoginApi

class LoginRepository(private val api: LoginApi) : BaseRepository() {

    suspend  fun login(username: String, password: String) = safeApiCall {
        api.login(username, password)
    }
}