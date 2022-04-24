package com.example.workouttracker

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workouttracker.databinding.ActivityListPlaylistBinding
import com.example.workouttracker.list.Playlist
import com.example.workouttracker.list.PlaylistsAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

var playlistList = mutableListOf<Playlist>()

lateinit var plAdapter: PlaylistsAdapter

class ListPlaylist : AppCompatActivity() {
    private lateinit var binding: ActivityListPlaylistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val file = File(this.filesDir, "playlists.ser")
        var ins: InputStream = file.inputStream()
        // read contents of IntputStream to String
        var content = ins.readBytes().toString(Charset.defaultCharset())
        playlistList = Json.decodeFromString(content)

        plAdapter = PlaylistsAdapter(playlistList, this)
        binding = ActivityListPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rv = findViewById<View>(R.id.recyc) as RecyclerView
        rv.adapter = plAdapter
        rv.layoutManager = LinearLayoutManager(this)

        binding.fab.setOnClickListener {
            var txt: EditText // user input bar
            val inputDialog: AlertDialog.Builder = AlertDialog.Builder(this)
            val editTextName1 = EditText(this@ListPlaylist)
            inputDialog.setTitle("New playlist")
            inputDialog.setView(editTextName1)
            val layoutName = LinearLayout(this)
            layoutName.orientation = LinearLayout.VERTICAL
            layoutName.addView(editTextName1) // displays the user input bar
            inputDialog.setView(layoutName)

            inputDialog.setPositiveButton(
                "Continue"
            ) { _, _ ->
                txt = editTextName1 // variable to collect user input
                val getInput = txt.text.toString()

                // ensure that user input bar is not empty
                if (getInput.trim() == ""){
                    Toast.makeText(baseContext, "Please add a playlist name", Toast.LENGTH_LONG).show()
                }
                // add input into an data collection arraylist
                else {
                    playlistList.add(Playlist(getInput, mutableListOf()))

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

                    plAdapter.notifyItemInserted(playlistList.size)
                }
            }

            inputDialog.setNegativeButton(
                "Cancel"
            ) { dialog, _ ->
                dialog.cancel() // closes dialog
            }

            editTextName1.hint = " Enter playlist name"
            inputDialog.show()
        }
    }
}