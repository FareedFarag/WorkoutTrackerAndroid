package com.example.workouttracker.database

class ProfileRepository(private val dao: ProfileDao) {

    suspend fun insertProfile(userProfile : ProfileEntity) {
        return dao.insertProfile(userProfile)
    }

    suspend fun getHeight(userid: String): String? {
        return dao.getHeight(userid)
    }
}