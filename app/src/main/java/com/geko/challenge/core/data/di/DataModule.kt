package com.geko.challenge.core.data.di

import com.geko.challenge.core.common.di.IoDispatcher
import com.geko.challenge.core.data.repository.AuthenticationRepository
import com.geko.challenge.core.database.dao.UserDao
import com.geko.challenge.core.datastore.AppDatastore
import com.geko.challenge.core.network.retrofit.RetrofitNetworkApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {

    @Provides
    fun bindsAuthRepository(
        store: AppDatastore,
        apiService: RetrofitNetworkApi,
        userDao: UserDao,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): AuthenticationRepository = AuthenticationRepository(store, apiService, userDao, ioDispatcher)
}