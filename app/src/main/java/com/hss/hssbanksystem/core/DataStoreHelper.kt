package com.hss.hssbanksystem.core

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreHelper(context: Context) {

    private val appContext = context.applicationContext
    private val dataStore: DataStore<Preferences> = appContext.createDataStore(
        name = SHARED_PREFERENCES_NAME
    )

    val authenticationToken: Flow<String?>
        get() = dataStore.data.map {
            it[TOKEN_KEY]
        }

    val username: Flow<String?>
        get() = dataStore.data.map {
            it[USERNAME_KEY]
        }

    val userType: Flow<Int?>
        get() = dataStore.data.map {
            it[USERTYPE_KEY]
        }

    suspend fun saveUserData(token: String, username: String, userType: Int){
        dataStore.edit{
            it[TOKEN_KEY] = token
            it[USERNAME_KEY] = username
            it[USERTYPE_KEY] = userType
        }
    }

    suspend fun clear(){
        dataStore.edit{
            it.clear()
        }
    }

    companion object {
        private  val TOKEN_KEY = preferencesKey<String>("token")
        private  val USERNAME_KEY = preferencesKey<String>("username")
        private  val USERTYPE_KEY = preferencesKey<Int>("userType")
        private const val SHARED_PREFERENCES_NAME = "com.hss.hssbanksystem"
    }
}

