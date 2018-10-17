package com.example.android.weatherapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceSelectionListener

class MainActivity : AppCompatActivity(), PlaceSelectionListener {

    companion object {
        private const val REQUEST_LOCATION = 100
        const val KEY_LOCATION_NAME = "location_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //pickanje preko mape -> startActivityForResult

        startActivityForResult(Intent(), REQUEST_LOCATION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_LOCATION && resultCode == Activity.RESULT_OK) {
            val selectedLocationName = data?.getStringExtra(KEY_LOCATION_NAME) ?: ""

            //todo search/prikazi vrijeme
        }


        //TODO ovo je sve u map activity
        val locationName = "k"

        val resultIntent = Intent().apply {
            putExtra(KEY_LOCATION_NAME, locationName)
        }

        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    override fun onPlaceSelected(place: Place?) {
        val name = place?.name ?: ""

        if (name.isNotBlank()) {
            //todo do somehting
        }
    }

    override fun onError(status: Status?) {
        Toast.makeText(this, status?.statusMessage ?: "Place not found", Toast.LENGTH_SHORT).show()
    }
}
