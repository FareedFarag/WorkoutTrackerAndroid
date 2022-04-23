package com.example.workouttracker.list

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workouttracker.ListWorkout
import com.example.workouttracker.databinding.ItemPlaylistBinding
import com.example.workouttracker.playlistListPosition

class PlaylistsAdapter(var playlists: List<Playlist>, var context: Context) : RecyclerView.Adapter<PlaylistsAdapter.PlaylistsViewHolder>() {
    inner class PlaylistsViewHolder(val binding: ItemPlaylistBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaylistBinding.inflate(layoutInflater, parent, false)
        return PlaylistsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistsViewHolder, position: Int) {
        holder.binding.apply {
            val current = playlists[position]
            textname.text = current.name
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ListWorkout::class.java)
            playlistListPosition = position
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return playlists.size
    }
}