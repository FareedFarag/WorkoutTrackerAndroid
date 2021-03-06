package com.example.workouttracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.DatePicker
import androidx.core.app.NavUtils
import com.example.workouttracker.list.Workout
import com.google.android.material.textfield.TextInputLayout
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.FieldPosition

var playlistWorkoutPosition = 0

class EditWorkoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_workout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val button = findViewById<Button>(R.id.save_change)
        button.setOnClickListener {
            itemAdded()
            val intent = Intent(this, ListPlaylist::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, ListWorkout::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun itemAdded() {
        val name = findViewById<TextInputLayout>(R.id.nameInputLayout).editText?.text.toString()
        val weight = findViewById<TextInputLayout>(R.id.weightInputLayout).editText?.text.toString()
        val sets = findViewById<TextInputLayout>(R.id.setInputLayout).editText?.text.toString()
        val reps = findViewById<TextInputLayout>(R.id.repInputLayout).editText?.text.toString()
        val note = findViewById<TextInputLayout>(R.id.descInputLayout).editText?.text.toString()

        playlistList[playlistListPosition].list[playlistWorkoutPosition] = Workout(
            name, weight.toInt(), sets.toInt(), reps.toInt(), note
        )

        val serList = Json.encodeToString(playlistList)
        val file = File(this.filesDir,"playlists.ser")

        try {
            FileOutputStream(file).use { fos ->
                fos.write(serList.toByteArray())
                println("Successfully written data to the file")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}