package fr.isen.volto.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import fr.isen.volto.androiderestaurant.R
import androidx.appcompat.app.AppCompatActivity


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        //Log.d("TAG", findViewById<TextView>(R.id.tap_listener))
        // get reference to button
        val home_tap = findViewById<TextView>(R.id.tap_listener)
        // set on-click listener
        home_tap.setOnClickListener {
            // your code to perform when the user clicks on the button
            val intent = Intent(this, RestaurantActivity::class.java);
            startActivity(intent);
        }

    }
}