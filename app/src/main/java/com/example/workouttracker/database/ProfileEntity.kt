package com.example.workouttracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey
    var userId: String,

    @ColumnInfo(name = "weight")
    var weight: Double?,

    @ColumnInfo(name = "height")
    var height: String?,

    @ColumnInfo(name = "age")
    var age: String?,

    @ColumnInfo(name = "gender")
    var gender: String?
)