package fr.isen.volto.androiderestaurant

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.gson.Gson
import java.io.FileReader


class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_activity)

        val orders = Gson().fromJson(FileReader(this.filesDir.toString()+"/"+"cart_informations.json"), Array<Order>::class.java)

        var recyclerView: RecyclerView = findViewById(R.id.recyclerViewCart);
        recyclerView.layoutManager = LinearLayoutManager(this);
        var adapterDishesActivity: AdapterCartActivity = AdapterCartActivity(0,orders);
        recyclerView.adapter = adapterDishesActivity


    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("debug", " destroyed") // log the destroy cycle
    }
}