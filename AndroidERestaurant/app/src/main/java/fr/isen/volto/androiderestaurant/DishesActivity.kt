package fr.isen.volto.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text


class DishesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dishes_activity)

        var page_name = findViewById<TextView>(R.id.page_name)

        val starter_button = findViewById<Button>(R.id.starter2)
        val dishes_button = findViewById<Button>(R.id.dishes2)
        val desserts_button = findViewById<Button>(R.id.desserts2)

        page_name.text = intent.getStringExtra("page_name")

        var s1: Array<String> = resources.getStringArray(R.array.products_name);
        var s2: Array<String> = resources.getStringArray(R.array.products_description);
        var s3: Array<String> = resources.getStringArray(R.array.products_ingredients);

        var recyclerView: RecyclerView = findViewById(R.id.recyclerView);

        var myAdapter: MyAdapter = MyAdapter(s1,s2,s3);
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this);

        if (page_name.text.toString() == "Les entrées") {
            starter_button.text = "Accueil"
            starter_button.setOnClickListener {
                val intent = Intent(this, RestaurantActivity::class.java);
                startActivity(intent);
            }
        }
        else
        {
            starter_button.setOnClickListener {
                val intent = Intent(this, DishesActivity::class.java);
                intent.putExtra("page_name","Les entrées")
                startActivity(intent);
            }
        }

        if (page_name.text.toString() == "Les plats") {
            dishes_button.text = "Accueil"
            dishes_button.setOnClickListener {
                val intent = Intent(this, RestaurantActivity::class.java);
                startActivity(intent);
            }
        }
        else
        {
            dishes_button.setOnClickListener {
                val intent = Intent(this, DishesActivity::class.java);
                intent.putExtra("page_name","Les plats")
                startActivity(intent);
            }
        }

        if (page_name.text.toString() == "Les desserts") {
            desserts_button.text = "Accueil"
            desserts_button.setOnClickListener {
                val intent = Intent(this, RestaurantActivity::class.java);
                startActivity(intent);
            }
        }
        else
        {
            desserts_button.setOnClickListener {
                val intent = Intent(this, DishesActivity::class.java);
                intent.putExtra("page_name","Les desserts")
                startActivity(intent);
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("debug", " destroyed") // log the destroy cycle
    }
}