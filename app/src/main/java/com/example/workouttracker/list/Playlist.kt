package com.example.workouttracker.list

data class Playlist(
    var name: String,
    var list: MutableList<Workout>
)
