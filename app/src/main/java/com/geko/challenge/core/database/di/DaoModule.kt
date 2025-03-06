package com.geko.challenge.core.database.di

import com.geko.challenge.core.database.ChallengeDatabase
import com.geko.challenge.core.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    fun providesTopicsDao(
        database: ChallengeDatabase,
    ): UserDao = database.userDao()
}