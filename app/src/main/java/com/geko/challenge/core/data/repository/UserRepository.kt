package com.geko.challenge.core.data.repository

import com.geko.challenge.core.common.di.IoDispatcher
import com.geko.challenge.core.data.common.DataResult
import com.geko.challenge.core.data.model.toUserModel
import com.geko.challenge.core.database.dao.UserDao
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    @IoDispatcher private val ioDispatcher: CoroutineContext
) {
    suspend fun getUserData() = withContext(ioDispatcher) {
        return@withContext try {
            DataResult.Success(userDao.getUser().toUserModel())
        } catch (e: Exception) {
            DataResult.Failed("${e.message}")
        }
    }
}