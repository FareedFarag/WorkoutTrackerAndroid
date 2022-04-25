package com.example.workouttracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0,

    @ColumnInfo(name = "firstName")
    var firstName: String,

    @ColumnInfo(name = "lastName")
    var lastName: String,

    @ColumnInfo(name = "username")
    var userName: String,

    @ColumnInfo(name = "password")
    var password: ByteArray
)

@Entity(tableName = "profile")
data class Profile(
    @PrimaryKey(autoGenerate = false)
    var userId: Int,

    @ColumnInfo(name = "weight")
    var weight: Double,

    @ColumnInfo(name = "height")
    var height: String,

    @ColumnInfo(name = "age")
    var age: String,

    @ColumnInfo(name = "gender")
    var gender: String
)