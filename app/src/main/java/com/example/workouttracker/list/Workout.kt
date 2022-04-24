package com.example.workouttracker.list

import kotlinx.serialization.Serializable

@Serializable
data class Workout(
    var name: String,
    var weight: Int,
    var sets: Int,
    var reps: Int,
    var note: String
)
