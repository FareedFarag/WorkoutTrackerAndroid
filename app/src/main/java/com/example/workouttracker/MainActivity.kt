package com.example.workouttracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {
    var sharedPreference:SharedPreference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreference = SharedPreference(this)
        val isLoggedIn = sharedPreference!!.getPreferenceString("isLoggedIn")
        val userid = sharedPreference!!.getPreferenceString("userID")
        if (userid != null) {
            // do whatever you want with the userid
        }

        // Redirect user to dashboard if user is already logged in
        if (isLoggedIn != null){
            val navHostFragment = (supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment)
            val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.navigation)
            graph.startDestination = R.id.userDetailsFragment
            navHostFragment.navController.graph = graph
        }
    }
}