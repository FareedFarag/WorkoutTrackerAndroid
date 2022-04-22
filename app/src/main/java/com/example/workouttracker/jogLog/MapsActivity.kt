package com.example.workouttracker.jogLog

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.workouttracker.R
import com.example.workouttracker.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var  fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: com.google.android.gms.location.LocationRequest
    private var requestingLocationUpdates: Boolean = false
    private var locationsarray: ArrayList<LatLng> = ArrayList()
    private var totalD: Float = 0f
    companion object{
        private const val LOCATION_REQUEST_CODE = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.donebtn.setOnClickListener{
            calculateDistance(locationsarray)
            Toast.makeText(applicationContext,"Done",Toast.LENGTH_SHORT).show()
            Toast.makeText(applicationContext, "Distance: $totalD",Toast.LENGTH_LONG).show()
            onPause()

        }


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations){

                    //Log.d("LocationUpdate", "Latitude = " + location.latitude + " Longitude = " + location.longitude)
                    // Update UI with location data
                    val mylocation = LatLng(location.latitude,location.longitude)
                    placeMarkerOnMap(mylocation)
                    // ...
                    locationsarray.add(mylocation)

                    if(locationsarray.size > 1) {
                        mMap.addPolyline(
                            PolylineOptions().add(
                                LatLng(locationsarray[locationsarray.size - 2].latitude, locationsarray[locationsarray.size - 2].longitude),
                                LatLng(locationsarray.last().latitude, locationsarray.last().longitude)
                            ).width(2f).color(Color.BLUE).geodesic(true)
                        )
                    }
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)

        setUpMap()
    }

    @SuppressLint("MissingPermission")
    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            return
        }
        Log.d("enabled? ", mMap.isMyLocationEnabled.toString())
        locationRequest = com.google.android.gms.location.LocationRequest.create()
        locationRequest.priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 10000
        requestingLocationUpdates = true

        mMap.isMyLocationEnabled = true
        startLocationUpdates()
    }
    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")
        //Log.d("location: ", currentLatLong.toString())
        mMap.addMarker(markerOptions)

    }

    override fun onResume() {
        super.onResume()
        if (requestingLocationUpdates) startLocationUpdates()
    }



    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun calculateDistance(points: ArrayList<LatLng>) {
        var tempTotalDistance: Float = 0f
        for (i in 0 until points.size - 1) {
            val pointA = points[i]
            val pointB = points[i + 1]
            val results = FloatArray(3)
            Location.distanceBetween(
                pointA.latitude,
                pointA.longitude,
                pointB.latitude,
                pointB.longitude,
                results
            )
            tempTotalDistance += results[0]
        }
        totalD = tempTotalDistance
    }





    override fun onMarkerClick(p0: Marker) = false
}