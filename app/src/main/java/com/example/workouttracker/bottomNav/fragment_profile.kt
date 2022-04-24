package com.example.workouttracker.bottomNav

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.workouttracker.R
import com.example.workouttracker.profile.AgeActivity
import com.example.workouttracker.profile.height
import com.example.workouttracker.profile.weight


class ProfileFragment:Fragment(R.layout.fragment_profile) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_profile, container)
        val heightCard = v.findViewById<CardView>(R.id.Height)
        val weightCard = v.findViewById<CardView>(R.id.Weight)
        val ageCard = v.findViewById<CardView>(R.id.Age)
        val genderCard = v.findViewById<CardView>(R.id.Gender)

        heightCard.setOnClickListener {
            val intent = Intent(activity, height::class.java)
            startActivity(intent)
        }
        weightCard.setOnClickListener {
            val intent = Intent(activity, weight::class.java)
            startActivity(intent)
        }
        ageCard.setOnClickListener {
            val intent = Intent(activity, AgeActivity::class.java)
            startActivity(intent)
        }
        genderCard.setOnClickListener {
            alertSingleChoiceItems()
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun alertSingleChoiceItems() {
        val builder = AlertDialog.Builder(context)

        // Set the dialog title
        builder.setTitle("Choose One") // specify the list array, the items to be selected by default (null for none),
            // and the listener through which to receive call backs when items are selected
            // again, R.array.choices were set in the resources res/values/strings.xml
            .setSingleChoiceItems(R.array.choices, 0,
                DialogInterface.OnClickListener { arg0, arg1 -> }) // Set the action buttons
            .setPositiveButton(
                "OK",
                DialogInterface.OnClickListener { dialog, id -> // user clicked OK, so save the mSelectedItems results somewhere
                    // or return them to the component that opened the dialog
                    val selectedPosition = (dialog as AlertDialog).listView.checkedItemPosition
                    //showToast("selectedPosition: $selectedPosition")
                })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                // removes the dialog from the screen
            })
            .show()
    }
}
