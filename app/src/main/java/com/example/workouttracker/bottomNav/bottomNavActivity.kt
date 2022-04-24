package com.example.workouttracker.bottomNav

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.example.workouttracker.*
import com.example.workouttracker.graph.graph
import com.example.workouttracker.jogLog.MapsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class bottomNavActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)

        val homeFragment= HomeFragment()
        val profileFragment= ProfileFragment()

        setCurrentFragment(homeFragment)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnItemSelectedListener{
            when (it.itemId) {
                R.id.home -> setCurrentFragment(homeFragment)
                R.id.joglog -> {
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                }
                R.id.add ->{
                    val intent = Intent(this, ListPlaylist::class.java)
                    startActivity(intent)

                }
                R.id.profile -> setCurrentFragment(profileFragment)
                R.id.graph -> {
                    val intent = Intent(this, graph::class.java)
                    startActivity(intent)
                }
            }
            true
        }


    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

}