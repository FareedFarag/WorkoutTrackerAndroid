package com.example.workouttracker.database

class UserRepository(private val dao: UserDao) {

    suspend fun insert(user: UserEntity) {
        return dao.insert(user)
    }

    suspend fun getUserByUsername(username: String):UserEntity?{
        return dao.getUserByUsername(username)
    }

    suspend fun getUserByUserID(userID: String):UserEntity?{
        return dao.getUserByUserID(userID)
    }
}