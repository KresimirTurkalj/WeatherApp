package com.example.android.weatherapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

/*This activity opens map and centers it on your current location.
* Once you select Location it closes and returns to Main Activity*/

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, PlaceSelectionListener {


    companion object {
        const val REQUEST_LOC_PERMISSION = 1
        const val KEY_LOCATION = "location"
        const val TAG = "MapsActivity"
    }

    private lateinit var currentLatLng: LatLng
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setupLocationClient()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getCurrentLocation()
    }

    private fun setupLocationClient() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOC_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_LOC_PERMISSION) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                Log.e(TAG, "Location permission denied")
            }
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermissions()
        } else {
            mMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnCompleteListener {
                if (it.result != null) {
                    val currentLatLng = LatLng(it.result!!.latitude, it.result!!.longitude)
                    val update = CameraUpdateFactory.newLatLngZoom(currentLatLng, 10.0f)
                    mMap.moveCamera(update)
                } else {
                    Log.e(TAG, "No location found")
                }
            }
        }
    }

    override fun onPlaceSelected(p0: Place?) {
        val resultIntent = Intent().apply { putExtra(KEY_LOCATION, currentLatLng) }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    override fun onError(p0: Status?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
