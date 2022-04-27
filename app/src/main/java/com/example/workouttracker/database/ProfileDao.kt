package com.example.workouttracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProfileDao {
    @Insert
    suspend fun insertProfile(userProfile: ProfileEntity)

    @Query ("UPDATE profile SET weight = :weight WHERE userId = :userid")
    suspend fun updateWeight(weight: Double, userid: String)

    @Query ("UPDATE profile SET height = :height WHERE userId = :userid")
    suspend fun updateHeight(height: String, userid: String)

    @Query("SELECT height FROM profile WHERE userId = :userid")
    suspend fun getHeight(userid: String): String?
}