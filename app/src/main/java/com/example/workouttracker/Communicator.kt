package com.example.workouttracker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//class Communicator : ViewModel(){
//
//    val message = MutableLiveData<Any>()
//
//    fun setMsgCommunicator(msg:String){
//        message.value = msg
//    }
//}

class SharedViewModel : ViewModel() {
    val selected = MutableLiveData<String>()

    fun select(name: String) {
        selected.value = name
    }
}