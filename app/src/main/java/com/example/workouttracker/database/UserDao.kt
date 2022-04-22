package com.example.workouttracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insert(register: UserEntity)

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Query("SELECT * FROM users WHERE username = :userID")
    suspend fun getUserByUserID(userID: Int): UserEntity?
}

@Dao
interface profileDao {
    @Insert
    suspend fun insertProfile(userProfile: Profile)
}