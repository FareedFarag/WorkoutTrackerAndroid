package com.example.workouttracker.list

import kotlinx.serialization.Serializable

@Serializable
data class Playlist(
    var name: String,
    var list: MutableList<Workout>
)
