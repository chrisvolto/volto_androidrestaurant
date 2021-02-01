package fr.isen.volto.androiderestaurant.Home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import fr.isen.volto.androiderestaurant.Cart.CartActivity
import fr.isen.volto.androiderestaurant.Dishes.DishesActivity
import fr.isen.volto.androiderestaurant.R


class RestaurantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        //intent.getStringExtra("page_name")

        // get reference to button
        val starter_button = findViewById(R.id.starter) as Button
        // set on-click listener
        starter_button.setOnClickListener {
            // your code to perform when the user clicks on the button
            val intent = Intent(this, DishesActivity::class.java);
            intent.putExtra("page_name","Entrées")
            startActivity(intent);
        }

        // get reference to button
        val dishies_button = findViewById(R.id.dishes) as Button
        // set on-click listener
        dishies_button.setOnClickListener {
            // your code to perform when the user clicks on the button
            val intent = Intent(this, DishesActivity::class.java);
            intent.putExtra("page_name","Plats")
            startActivity(intent);
        }

        // get reference to button
        val desserts_button = findViewById(R.id.desserts) as Button
        // set on-click listener
        desserts_button.setOnClickListener {
            // your code to perform when the user clicks on the button
            val intent = Intent(this, DishesActivity::class.java);
            intent.putExtra("page_name","Desserts")
            startActivity(intent);
        }

    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.my_options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, CartActivity::class.java);
        startActivity(intent);
        return true;
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.i("debug", " destroyed") // log the destroy cycle
    }
}