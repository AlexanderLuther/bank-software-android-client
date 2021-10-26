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

    /**
     * Funcion que guarda los datos del usuario logeado
     * @param token Token del usuario
     * @param username Nombre de usuario del usuario
     */
    suspend fun saveUserData(token: String, username: String){
        dataStore.edit{
            it[TOKEN_KEY] = token
            it[USERNAME_KEY] = username
        }
    }

    /**
     * Funcion que elimina todos los datos almacenados del usuario
     */
    suspend fun clear(){
        dataStore.edit{
            it.clear()
        }
    }

    companion object {
        private  val TOKEN_KEY = preferencesKey<String>("token")
        private  val USERNAME_KEY = preferencesKey<String>("username")
        private const val SHARED_PREFERENCES_NAME = "com.hss.hssbanksystem"
    }
}