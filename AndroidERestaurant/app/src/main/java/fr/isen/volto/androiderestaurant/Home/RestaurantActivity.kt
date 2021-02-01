package fr.isen.volto.androiderestaurant.Home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import fr.isen.volto.androiderestaurant.Cart.CartActivity
import fr.isen.volto.androiderestaurant.Dishes.DishesActivity
import fr.isen.volto.androiderestaurant.Order
import fr.isen.volto.androiderestaurant.R
import java.io.FileReader
import kotlin.math.min


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
        getMenuInflater().inflate(R.menu.my_options_menu, menu);

        var orders:Array<Order> = try {
            Gson().fromJson(FileReader(this.filesDir.toString() + "/" + "cart_informations.json"), Array<Order>::class.java)
        }catch (e: Exception)
        {
            emptyArray()
        }
        var n:Int = orders.size;

        var menuItem: MenuItem? = menu?.findItem(R.id.action_cart)
        if( menuItem != null) {
            val actionView: View = menuItem.actionView
            val textCartItemCount = actionView.findViewById<View>(R.id.cart_badge) as TextView
            if (n == 0) {
                if (textCartItemCount.visibility != View.GONE) {
                    textCartItemCount.visibility = View.GONE;
                }
            } else {
                textCartItemCount.text = min(n, 99).toString();
                if (textCartItemCount.visibility != View.VISIBLE) {
                    textCartItemCount.visibility = View.VISIBLE;
                }
            }
            actionView.setOnClickListener {
                onOptionsItemSelected(menuItem)
            }
            return true
        }

        return false
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