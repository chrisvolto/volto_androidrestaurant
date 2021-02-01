package fr.isen.volto.androiderestaurant.Index

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.isen.volto.androiderestaurant.R
import fr.isen.volto.androiderestaurant.Home.RestaurantActivity


class IndexActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.index_activity)

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

    override fun onDestroy() {
        super.onDestroy()
        Log.i("debug", " destroyed") // log the destroy cycle
    }
}