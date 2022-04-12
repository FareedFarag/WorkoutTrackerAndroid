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

        sharedPreference =SharedPreference(this)

        // Redirect user to dashboard if user is already logged in
        val isLoggedIn = sharedPreference!!.getPreferenceString("isLoggedIn")

        if (isLoggedIn != null){
            val navHostFragment = (supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment)
            val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.navigation)
            graph.startDestination = R.id.userDetailsFragment
            navHostFragment.navController.graph = graph
        }
    }

//    private var doubleBackToExitPressedOnce = false
//    override fun onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed()
//            Log.d("pref", "back")
//            return
//        }
//
//        this.doubleBackToExitPressedOnce = true
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
//
//        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
//    }

}