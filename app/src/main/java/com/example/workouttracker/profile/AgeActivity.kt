package com.example.workouttracker.profile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.workouttracker.MainActivity
import com.example.workouttracker.R
import com.example.workouttracker.SharedPreference
import com.example.workouttracker.bottomNav.bottomNavActivity
import java.util.*


class AgeActivity : AppCompatActivity() {

    var sharedPreference: SharedPreference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age)
        val saveButton = findViewById<Button>(R.id.save)
        val cancelButton = findViewById<Button>(R.id.cancel)
        val Age = findViewById<EditText>(R.id.Age)

        sharedPreference = SharedPreference(this)
        val userid = sharedPreference!!.getPreferenceString("userID")
        var age = sharedPreference!!.getPreferenceString("age")

        saveButton.setOnClickListener {
            if (userid != null) {
                age = Age.toString()
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        cancelButton.setOnClickListener {
            val intent = Intent(this, bottomNavActivity::class.java)
            startActivity(intent)
        }

    }
}
