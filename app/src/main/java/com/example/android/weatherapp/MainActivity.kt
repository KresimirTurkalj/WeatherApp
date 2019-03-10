package com.example.android.weatherapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.activity_main.*


/**Main Activity that contains refresh button, map button and PlaceAutoComplete fragment
 * Still need to figure out where to place a simple button that adds current location
 * and how to place button for Activity that holds info*/

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_LOCATION = 1
        const val KEY_LOCATION = "location"
    }

    private var currentLatLng: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Places.initialize(applicationContext, getString(R.string.apiKey))
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(arrayListOf(Place.Field.LAT_LNG))
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place) {
                currentLatLng = p0.latLng
                if (currentLatLng != null) {
                    Toast.makeText(
                        baseContext,
                        "Lat: ${currentLatLng!!.latitude}, Lon: ${currentLatLng!!.longitude}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onError(p0: Status) {
                Toast.makeText(baseContext, "Place error", Toast.LENGTH_LONG).show()
            }
        })

        map_button.setOnClickListener {
            val mapIntent = Intent(this, MapsActivity::class.java)
            startActivityForResult(mapIntent, REQUEST_LOCATION)
        }

        refresh_button.setOnClickListener {
            //TODO Refresh information for last selected place.
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_LOCATION && resultCode == Activity.RESULT_OK) {
            currentLatLng = data!!.getParcelableExtra(KEY_LOCATION)
        }
        if (currentLatLng != null) {
            Toast.makeText(
                this, "Lat: ${currentLatLng!!.latitude}, Lon: ${currentLatLng!!.longitude}", Toast.LENGTH_LONG
            ).show()
        }
    }
}
