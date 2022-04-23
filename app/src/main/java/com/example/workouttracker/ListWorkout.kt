package com.example.workouttracker

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workouttracker.list.Workout
import com.example.workouttracker.list.WorkoutsAdapter
import com.example.workouttracker.databinding.ActivityListWorkoutBinding

var playlistListPosition = 0
lateinit var woAdapter: WorkoutsAdapter

class ListWorkout : AppCompatActivity() {
    private lateinit var binding: ActivityListWorkoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        woAdapter = WorkoutsAdapter(playlistList[playlistListPosition].list, this)
        binding = ActivityListWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.fab.setOnClickListener {
            val intent = Intent(this, AddWorkoutActivity::class.java)
            startActivity(intent)
        }

        val rv = findViewById<View>(R.id.recyc) as RecyclerView
        rv.adapter = woAdapter
        rv.layoutManager = LinearLayoutManager(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, ListPlaylist::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}