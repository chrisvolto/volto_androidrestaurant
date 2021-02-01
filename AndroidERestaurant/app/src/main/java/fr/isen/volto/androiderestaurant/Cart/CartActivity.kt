package fr.isen.volto.androiderestaurant.Cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.gson.Gson
import fr.isen.volto.androiderestaurant.Order
import fr.isen.volto.androiderestaurant.R
import fr.isen.volto.androiderestaurant.Home.RestaurantActivity
import java.io.FileReader
import java.lang.Exception


class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_activity)

        var orders:Array<Order> = try {
            Gson().fromJson(FileReader(this.filesDir.toString() + "/" + "cart_informations.json"), Array<Order>::class.java)
        }catch (e: Exception)
        {
            emptyArray()
        }

        var recyclerView: RecyclerView = findViewById(R.id.recyclerViewCart);
        recyclerView.layoutManager = LinearLayoutManager(this);
        var adapterDishesActivity = AdapterCartActivity(0, orders);
        recyclerView.adapter = adapterDishesActivity

        var homeButton: Button = findViewById(R.id.cart_home);
        homeButton.setOnClickListener {
            val intent = Intent(this, RestaurantActivity::class.java);
            startActivity(intent);
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("debug", " destroyed") // log the destroy cycle
    }
}