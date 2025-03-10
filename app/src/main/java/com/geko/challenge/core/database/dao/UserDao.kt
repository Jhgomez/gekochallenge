package com.geko.challenge.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.geko.challenge.core.database.model.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): UserEntity

    @Query("SELECT * FROM USER LIMIT 1")
    fun getUser(): UserEntity

    @Insert
    fun insert(user: UserEntity)

    @Query("DELETE FROM user")
    fun delete()
}