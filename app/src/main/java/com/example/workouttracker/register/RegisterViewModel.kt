package com.example.workouttracker.register

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.workouttracker.database.UserEntity
import com.example.workouttracker.database.UserRepository
import com.toxicbakery.bcrypt.Bcrypt
import de.nycode.bcrypt.hash
import kotlinx.coroutines.*


class RegisterViewModel(private val repository: UserRepository, application: Application) :
    AndroidViewModel(application), Observable {

    private var userdata: String? = null

    var userDetailsLiveData = MutableLiveData<Array<UserEntity>>()

    @Bindable
    val inputFirstName = MutableLiveData<String>()

    @Bindable
    val inputLastName = MutableLiveData<String>()

    @Bindable
    val inputUsername = MutableLiveData<String>()

    @Bindable
    val inputPassword = MutableLiveData<String>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val _navigateTo = MutableLiveData<Boolean>()

    val navigateTo: LiveData<Boolean>
        get() = _navigateTo

    private val _navigateToLogin = MutableLiveData<Boolean>()

    val navigateToLogin: LiveData<Boolean>
        get() = _navigateToLogin

    private val _errorToast = MutableLiveData<Boolean>()

    val errorToast: LiveData<Boolean>
        get() = _errorToast

    private val _errorToastUsername = MutableLiveData<Boolean>()

    val errorToastUsername: LiveData<Boolean>
        get() = _errorToastUsername

    fun login() {
        _navigateToLogin.value = true
    }

    fun submitButton() {
        if (inputFirstName.value == null || inputLastName.value == null || inputUsername.value == null || inputPassword.value == null) {
            _errorToast.value = true
        } else {
            uiScope.launch {
//            withContext(Dispatchers.IO) {
                val usernameCheck = repository.getUserByUsername(inputUsername.value!!)
                if (usernameCheck != null) {
                    _errorToastUsername.value = true
                } else {
                    val firstName = inputFirstName.value!!
                    val lastName = inputLastName.value!!
                    val email = inputUsername.value!!
                    val password = inputPassword.value!!
//                    val hash = BCryptPasswordEncoder().encode(password)
                    val hash = Bcrypt.hash(password, 10)
//                    val hash = hash(inputPassword.value!!)
//                    Log.d("hash", hash.toString())
                    insert(UserEntity(0, firstName, lastName, email, hash))
                    inputFirstName.value = null
                    inputLastName.value = null
                    inputUsername.value = null
                    inputPassword.value = null
                    _navigateTo.value = true
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun doneNavigatingLogin() {
        _navigateToLogin.value = false
    }

    fun doneNavigating() {
        _navigateTo.value = false
    }

    fun doneToast() {
        _errorToast.value = false
    }

    fun doneToastUserName() {
        _errorToast.value = false
    }

    private fun insert(user: UserEntity): Job = viewModelScope.launch {
        repository.insert(user)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}