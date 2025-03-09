package com.geko.challenge.core.data.repository

import android.util.Log
import com.geko.challenge.core.common.di.IoDispatcher
import com.geko.challenge.core.data.common.DataResult
import com.geko.challenge.core.data.model.toUserEntity
import com.geko.challenge.core.data.model.toUserModel
import com.geko.challenge.core.database.dao.UserDao
import com.geko.challenge.core.database.model.UserEntity
import com.geko.challenge.core.datastore.AppDatastore
import com.geko.challenge.core.model.User
import com.geko.challenge.core.network.retrofit.RetrofitNetworkApi
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AuthenticationRepository @Inject constructor(
    private val store: AppDatastore,
    private val apiService: RetrofitNetworkApi,
    private val userDao: UserDao,
    @IoDispatcher private val ioDispatcher: CoroutineContext
) {
    private var alternative_result: Boolean? = false

    val isAuthenticated = store.isUserLoggedIn

    private suspend fun setIsAuthenticated(authenticated: Boolean) {
        store.setIsUserLoggedIn(authenticated)
    }

    suspend fun authenticate(
        email: String,
        password: String,
    ): DataResult<User> = withContext(ioDispatcher) {
        val parameters = mutableMapOf(
            "email" to email,
            "password" to password
        )

        // TODO Use either a wrapper or create a response adapter to retrofit
        try {
            val result = apiService.login(parameters)

            val entity = result.data.toUserEntity()
            userDao.insert(entity)

            return@withContext DataResult.Success(userDao.getUser().toUserModel())
        } catch (e: Exception) {

            return@withContext when(alternative_result) {
                true -> {
                    // means user logged in
                    alternative_result = false
                    setIsAuthenticated(true)
                    userDao.insert(UserEntity(name = "Juan"))

                    DataResult.Success(userDao.getUser().toUserModel())
                }
                false -> {
                    // means http error like wrong credentials
                    alternative_result = null
                    setIsAuthenticated(false)

                    DataResult.Failed("Bad credentials")
                }
                else -> {
                    // other errors
                    alternative_result = true
                    setIsAuthenticated(false)

                    DataResult.UnknownFailure
                }
            }
        }
    }

    suspend fun requestForgotPassword(email: String): DataResult<Unit> =
        withContext(ioDispatcher) {
            val parameters = mapOf(
                "email" to email,
            )

            // TODO Use either a wrapper or create a response adapter to retrofit
            try {
                apiService.resetPassword(parameters)
                return@withContext DataResult.Success(Unit)
            } catch (e: Exception) {

                return@withContext when(alternative_result) {
                    true -> {
                        // Keep moving alternatively
                        alternative_result = false

                        DataResult.Success(Unit)
                    }
                    false -> {
                        // Keep moving alternatively
                        alternative_result = null

                        DataResult.Success(Unit)
                    }
                    else -> {
                        // Keep moving alternatively
                        alternative_result = true

                        DataResult.Failed("Unknown Error, try again")
                    }
                }
            }
        }


    suspend fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): DataResult<User> = withContext(ioDispatcher) {
        val parameters = mapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "password" to password
        )

        try {
            val result = apiService.signUp(parameters)

            val entity = result.data.toUserEntity()
            userDao.insert(entity)

            return@withContext DataResult.Success(userDao.findByName(firstName).toUserModel())
        } catch (e: Exception) {

            return@withContext when(alternative_result) {
                true -> {
                    // means user logged in
                    alternative_result = false
                    setIsAuthenticated(true)
                    userDao.insert(UserEntity(name = firstName))

                    DataResult.Success(userDao.findByName(firstName).toUserModel())
                }
                false -> {
                    // means http error like wrong credentials
                    alternative_result = null
                    setIsAuthenticated(false)

                    DataResult.Failed("Server error")
                }
                else -> {
                    // other errors
                    alternative_result = true
                    setIsAuthenticated(false)

                    DataResult.UnknownFailure
                }
            }
        }
    }

    suspend fun logout() = withContext(ioDispatcher) {
        try {
            apiService.logout()
            setIsAuthenticated(false)
            userDao.delete()
        } catch (e: Exception) {
            setIsAuthenticated(false)
            userDao.delete()
            Log.d("AuthRepo", "User logged out")
        }
    }
}