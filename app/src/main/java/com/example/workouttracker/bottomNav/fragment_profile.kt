package com.example.workouttracker.bottomNav

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import com.example.workouttracker.R
import com.example.workouttracker.SharedPreference
import com.example.workouttracker.database.ProfileDao
import com.example.workouttracker.database.ProfileRepository
import com.example.workouttracker.database.UserDatabase
import com.example.workouttracker.profile.AgeActivity
import com.example.workouttracker.profile.height
import com.example.workouttracker.profile.weight
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class ProfileFragment()
    :Fragment(R.layout.fragment_profile) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_profile, container, false)
        val heightCard = v.findViewById<CardView>(R.id.Height)
        val weightCard = v.findViewById<CardView>(R.id.Weight)
        val ageCard = v.findViewById<CardView>(R.id.Age)
        val genderCard = v.findViewById<CardView>(R.id.Gender)

        val job = Job()
        val uiScope = CoroutineScope(Dispatchers.Main + job)

        val profileDao = UserDatabase.getInstance(requireContext()).profileDao
        Log.d("dao", profileDao.toString())

        val h = v.findViewById<TextView>(R.id.height1)
//        var height2: Double

        uiScope.launch(Dispatchers.Main) {
            // get height from database
            val sharedPreference = activity?.let { SharedPreference(it) }
            val userID = sharedPreference?.getPreferenceString("userID")
            Log.d("height_userIDget", userID.toString())

//            val height2 = userID?.let { profileDao.getHeight(it.toInt()) }
            val height2 = userID?.let { profileDao.getHeight(it) }

            h.text = height2
            if (height2 != null) {
                Log.d("height2", height2)
            }

        }

        val sharedPreference = activity?.let { SharedPreference(it) }

        heightCard.setOnClickListener {
            val intent = Intent(activity, height::class.java)
            addResultLauncher.launch(intent)

            Log.d("heightTest", "aa")
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
        return v //super.onCreateView(inflater, container, savedInstanceState)
    }

    var addResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val height = data?.getStringExtra("height") as String
            Log.d("height", height)

            val sharedPreference = activity?.let { SharedPreference(it) }
            val userID = sharedPreference?.getPreferenceString("userID")

            Log.d("height_userID", userID.toString())
            val job = Job()
            val uiScope = CoroutineScope(Dispatchers.Main + job)

            // get dao instance
            val profileDao = UserDatabase.getInstance(requireContext()).profileDao

            uiScope.launch(Dispatchers.Main) {
                // insert height into database
                if (userID != null) {
                    profileDao.updateHeight(height, userID)
                }
            }
        }
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
