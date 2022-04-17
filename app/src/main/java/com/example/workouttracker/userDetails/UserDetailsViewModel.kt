package com.example.workouttracker.userDetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workouttracker.SharedPreference
import com.example.workouttracker.database.UserRepository

class UserDetailsViewModel (private val repository: UserRepository,application: Application):AndroidViewModel(application){

//    val users = repository.getUserByUsername()

    private val _navigateTo = MutableLiveData<Boolean>()
    var sharedPreference = SharedPreference(application)

    val navigateTo: LiveData<Boolean>
        get() = _navigateTo

    fun doneNavigating(){
        _navigateTo.value=false
    }

    fun backButtonClicked(){
        sharedPreference!!.clearSharedPreference()
        _navigateTo.value = true
    }
}