package com.geko.challenge.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geko.challenge.core.database.dao.UserDao
import com.geko.challenge.core.database.model.UserEntity

@Database(
    entities = [
        UserEntity::class,
    ],
    version = 1,
    exportSchema = false
)
internal abstract class ChallengeDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
