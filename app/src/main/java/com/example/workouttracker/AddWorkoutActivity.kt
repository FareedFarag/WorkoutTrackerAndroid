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
import java.text.FieldPosition

class AddWorkoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_workout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val button = findViewById<Button>(R.id.save_change)
        button.setOnClickListener {
            itemAdded()
            val intent = Intent(this, ListWorkout::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
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

        playlistList[playlistListPosition].list.add(Workout(
            name, weight.toInt(), sets.toInt(), reps.toInt(), note
        ))
        woAdapter.notifyItemInserted(playlistList[playlistListPosition].list.size)
    }
}