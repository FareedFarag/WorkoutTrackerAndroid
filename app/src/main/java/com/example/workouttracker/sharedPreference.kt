package com.example.workouttracker

import android.content.Context
import android.content.SharedPreferences

class SharedPreference (val context: Context) {
    private val preferenceName = "secret"
    val sharedPref: SharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)

    fun saveString(key_name: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(key_name, text)
        editor.commit()
    }

    fun getPreferenceString(key_name: String): String? {
        return sharedPref.getString(key_name, null)
    }

    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.commit()
    }
}