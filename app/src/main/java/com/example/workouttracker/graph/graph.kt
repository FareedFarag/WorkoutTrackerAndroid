package com.example.workouttracker.graph

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.internal.ContextUtils.getActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.example.workouttracker.R
import com.example.workouttracker.profile.weight
import com.example.workouttracker.profile.weightList


class graph: AppCompatActivity()  {
    // creating a variable
    // for our graph view.
    lateinit var graphView: GraphView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_graph)

        // on below line we are initializing our graph view.
        graphView = findViewById(R.id.idGraphView)

        // on below line we are adding data to our graph view.
        var dataPointsList = mutableListOf<DataPoint>()

        for(wd in weightList) {
            dataPointsList.add(DataPoint(wd.date, wd.weight))
        }

        val series = LineGraphSeries(
            dataPointsList.toSortedSet(compareBy {it.x}).toTypedArray()
        )

        graphView.gridLabelRenderer.numHorizontalLabels = dataPointsList.toSortedSet(compareBy {it.x}).toTypedArray().size
        // after adding data to our line graph series.
        // on below line we are setting
        // title for our graph view.
        graphView.setTitle("My Graph View")

        // on below line we are setting
        // text color to our graph view.
        graphView.setTitleColor(android.R.color.black)

        // on below line we are setting
        // our title text size.
        graphView.setTitleTextSize(18f)

        // on below line we are adding
        // data series to our graph view.
        graphView.addSeries(series)
        graphView.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(this)
        //graphView.viewport.setMaxX(dataPointsList.last().x)
        //graphView.viewport.setMinX(dataPointsList[dataPointsList.size - 8].x)
        //graphView.viewport.setMaxX(dataPointsList.last().)

    }
}