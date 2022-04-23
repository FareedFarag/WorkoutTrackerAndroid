package com.example.workouttracker.login

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.example.workouttracker.SharedPreference
import com.example.workouttracker.database.UserRepository
import com.toxicbakery.bcrypt.Bcrypt
import kotlinx.coroutines.*

class LoginViewModel(private val repository: UserRepository, application: Application) :
    AndroidViewModel(application), Observable {

    var sharedPreference =SharedPreference(application)

    @Bindable
    val inputUsername = MutableLiveData<String>()

    @Bindable
    val inputPassword = MutableLiveData<String>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToRegister = MutableLiveData<Boolean>()

    val navigateToRegister: LiveData<Boolean>
        get() = _navigateToRegister

    private val _navigateToUserDetails = MutableLiveData<Boolean>()

    val navigateToUserDetails: LiveData<Boolean>
        get() = _navigateToUserDetails

    private val _errorToast = MutableLiveData<Boolean>()

    val errorToast: LiveData<Boolean>
        get() = _errorToast

    private val _errorToastUsername = MutableLiveData<Boolean>()

    val errorToastUsername: LiveData<Boolean>
        get() = _errorToastUsername

    private val _errorToastInvalidPassword = MutableLiveData<Boolean>()

    val errorToastInvalidPassword: LiveData<Boolean>
        get() = _errorToastInvalidPassword

    fun signUp() {
        _navigateToRegister.value = true
    }

    fun loginButton() {
        if (inputUsername.value == null || inputPassword.value == null) {
            _errorToast.value = true
        } else {
            uiScope.launch {

                val checkUsername = repository.getUserByUsername(inputUsername.value!!)
                if (checkUsername != null) {
                    if( Bcrypt.verify(inputPassword.value!!, checkUsername.password)){
                        inputUsername.value = null
                        inputPassword.value = null
                        sharedPreference.saveString("isLoggedIn","1")
                        sharedPreference.saveString("firstName", checkUsername.firstName)

                        _navigateToUserDetails.value = true
                    }else{
                        _errorToastInvalidPassword.value = true
                    }
                } else {
                    _errorToastUsername.value = true
                }
            }
        }
    }

    fun doneNavigatingRegister() {
        _navigateToRegister.value = false
    }

    fun doneNavigatingUserDetails() {
        _navigateToUserDetails.value = false
    }

    fun doneToast() {
        _errorToast.value = false
    }

    fun doneToastErrorUsername() {
        _errorToastUsername .value = false
    }

    fun doneToastInvalidPassword() {
        _errorToastInvalidPassword .value = false
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}