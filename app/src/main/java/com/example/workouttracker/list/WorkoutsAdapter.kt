package com.example.workouttracker.list

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workouttracker.EditWorkoutActivity
import com.example.workouttracker.databinding.ItemWorkoutBinding
import com.example.workouttracker.playlistListPosition
import com.example.workouttracker.playlistWorkoutPosition

class WorkoutsAdapter(var workouts: List<Workout>, var context: Context) : RecyclerView.Adapter<WorkoutsAdapter.WorkoutsViewHolder>() {
    inner class WorkoutsViewHolder(val binding: ItemWorkoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemWorkoutBinding.inflate(layoutInflater, parent, false)
        return WorkoutsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutsViewHolder, position: Int) {
        holder.binding.apply {
            val current = workouts[position]
            textv.text = current.name
            textweight.text = current.weight.toString()
            textsets.text = current.sets.toString()
            textreps.text = current.reps.toString()
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, EditWorkoutActivity::class.java)
            playlistWorkoutPosition = position
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return workouts.size
    }
}