package com.geko.challenge.core.database.di

import android.content.Context
import androidx.room.Room
import com.geko.challenge.core.database.ChallengeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesNiaDatabase(
        @ApplicationContext context: Context,
    ): ChallengeDatabase = Room.databaseBuilder(
        context,
        ChallengeDatabase::class.java,
        "nia-database",
    ).build()
}