package com.example.workouttracker.userDetails

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workouttracker.SharedPreference
import com.example.workouttracker.database.UserRepository
import com.example.workouttracker.jogLog.MapsActivity

class UserDetailsViewModel (private val repository: UserRepository,application: Application):AndroidViewModel(application){
//    val users = repository.getUserByUsername()
    private val _navigateTo = MutableLiveData<Boolean>()
    private val _switchActivity = MutableLiveData<Boolean>()
    var sharedPreference = SharedPreference(application)

    val navigateTo: LiveData<Boolean>
        get() = _navigateTo

    val switchActivity: LiveData<Boolean>
        get() = _switchActivity

    fun doneNavigating(){
        _navigateTo.value=false
    }

    fun backButtonClicked(){
        sharedPreference!!.clearSharedPreference()
        _navigateTo.value = true
    }

    fun switchActivity() {
        _switchActivity.value = true
    }
}