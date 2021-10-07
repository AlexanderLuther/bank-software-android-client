package com.hss.hssbanksystem.data.repository

import com.hss.hssbanksystem.core.DataStoreHelper
import com.hss.hssbanksystem.data.network.UserApi

class UserRepository(
    private val api: UserApi,
): BaseRepository() {


}