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


var heightList = mutableListOf<HeightDate>(
)

class height : AppCompatActivity() {

    lateinit var calendarView: CalendarView
    var currSelectedDay: Int = -1
    var currSelectedMonth: Int = -1
    var currSelectedYear: Int = -1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_height)
        calendarView = findViewById(R.id.calendar)
        val saveButton = findViewById<Button>(R.id.save)
        val cancelButton = findViewById<Button>(R.id.cancel)

        currSelectedYear = YearMonth.now().year
        currSelectedMonth = YearMonth.now().monthValue
        currSelectedDay = MonthDay.now().dayOfMonth
1
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
            val height: Double = findViewById<EditText>(R.id.height).text.toString().toDouble()

            if(currSelectedDay >= 0 && currSelectedMonth >= 0 && currSelectedYear >= 0 && height > 0) {
                val dateToAdd = Date(currSelectedYear, currSelectedMonth, currSelectedDay)
                heightList.add(HeightDate(height, dateToAdd))

                val occurenceOfDate = heightList.indexOfFirst {it.date == dateToAdd}

                if(occurenceOfDate != heightList.lastIndex) {
                    heightList.removeAt(occurenceOfDate)
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else {1
                Toast.makeText(
                    applicationContext,
                    "Invalid input",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}