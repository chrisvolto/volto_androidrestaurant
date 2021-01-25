package fr.isen.volto.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class RestaurantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.restaurant_activity)

        //intent.getStringExtra("page_name")

        // get reference to button
        val starter_button = findViewById(R.id.starter) as Button
        // set on-click listener
        starter_button.setOnClickListener {
            // your code to perform when the user clicks on the button
            val intent = Intent(this, DishesActivity::class.java);
            intent.putExtra("page_name","Les entrées")
            startActivity(intent);
        }

        // get reference to button
        val dishies_button = findViewById(R.id.dishes) as Button
        // set on-click listener
        dishies_button.setOnClickListener {
            // your code to perform when the user clicks on the button
            val intent = Intent(this, DishesActivity::class.java);
            intent.putExtra("page_name","Les plats")
            startActivity(intent);
        }

        // get reference to button
        val desserts_button = findViewById(R.id.desserts) as Button
        // set on-click listener
        desserts_button.setOnClickListener {
            // your code to perform when the user clicks on the button
            val intent = Intent(this, DishesActivity::class.java);
            intent.putExtra("page_name","Les desserts")
            startActivity(intent);
        }

    }
}