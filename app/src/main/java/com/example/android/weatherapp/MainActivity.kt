package com.example.android.weatherapp

import addFragment
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*

/*Main Activity that contains refresh button, map button and PlaceAutoComplete fragment
* Still need to figure out where to place a simple that adds current location and how to place button for Activity that holds info*/

class MainActivity : AppCompatActivity(), PlaceSelectionListener {

    companion object {
        const val REQUEST_LOCATION = 1
        const val KEY_LOCATION = "location"
    }

    lateinit var currentLatLng: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        map_button.setOnClickListener {
            val mapIntent = Intent(this, MapsActivity::class.java)
            startActivityForResult(mapIntent, REQUEST_LOCATION)
        }

        addFragment(autocomplete_fragment, R.id.card_view)
    }

    override fun onPlaceSelected(p0: Place?) {
        currentLatLng = p0!!.latLng

    }

    override fun onError(p0: Status?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_LOCATION && resultCode == Activity.RESULT_OK) {
            currentLatLng = data!!.getParcelableExtra(KEY_LOCATION)

        }
    }
}
