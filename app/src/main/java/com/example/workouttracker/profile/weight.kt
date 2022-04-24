package com.example.workouttracker.profile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.workouttracker.MainActivity
import java.util.*
import java.time.YearMonth
import java.time.MonthDay
import com.example.workouttracker.R
import com.example.workouttracker.bottomNav.bottomNavActivity


var weightList = mutableListOf<WeightDate>(

)

class weight : AppCompatActivity() {

    lateinit var calendarView: CalendarView
    var currSelectedDay: Int = -1
    var currSelectedMonth: Int = -1
    var currSelectedYear: Int = -1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight)
        calendarView = findViewById(R.id.calendar)
        val saveButton = findViewById<Button>(R.id.save)
        val cancelButton = findViewById<Button>(R.id.cancel)

        currSelectedYear = YearMonth.now().year
        currSelectedMonth = YearMonth.now().monthValue
        currSelectedDay = MonthDay.now().dayOfMonth

        calendarView.setOnDateChangeListener { arg0, year, month, date ->
            Toast.makeText(
                applicationContext,
                "$date/$month/$year",
                Toast.LENGTH_LONG
            ).show()

            currSelectedDay = date
            currSelectedMonth = month
            currSelectedYear = year
        }

       cancelButton.setOnClickListener {
            val intent = Intent(this, bottomNavActivity::class.java)
            startActivity(intent)
        }

        saveButton.setOnClickListener {
            val weight: Double = findViewById<EditText>(R.id.enter_weight_edit_text).text.toString().toDouble()

            if(currSelectedDay >= 0 && currSelectedMonth >= 0 && currSelectedYear >= 0 && weight > 0) {
                val dateToAdd = Date(currSelectedYear, currSelectedMonth, currSelectedDay)
                weightList.add(WeightDate(weight, dateToAdd))

                val occurenceOfDate = weightList.indexOfFirst {it.date == dateToAdd}

                if(occurenceOfDate != weightList.lastIndex) {
                    weightList.removeAt(occurenceOfDate)
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(
                    applicationContext,
                    "Invalid input",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}