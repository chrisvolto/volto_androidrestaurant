package fr.isen.volto.androiderestaurant.Dishes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.volto.androiderestaurant.Cart.CartActivity
import fr.isen.volto.androiderestaurant.Home.RestaurantActivity
import fr.isen.volto.androiderestaurant.Menu
import fr.isen.volto.androiderestaurant.Order
import fr.isen.volto.androiderestaurant.R
import org.json.JSONObject
import java.io.FileReader
import kotlin.math.min


class DishesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dishes_activity)

        var page_name = findViewById<TextView>(R.id.page_name)

        val starter_button = findViewById<Button>(R.id.starter2)
        val dishes_button = findViewById<Button>(R.id.dishes2)
        val desserts_button = findViewById<Button>(R.id.desserts2)

        page_name.text = intent.getStringExtra("page_name")
        //RequestQueue initialized
        var mRequestQueue = Volley.newRequestQueue(this)
        var recyclerView: RecyclerView = findViewById(R.id.recyclerView);
        recyclerView.layoutManager = LinearLayoutManager(this);

        //String Request initialized
        var mStringRequest = object : StringRequest(
            Request.Method.POST,
            "http://test.api.catering.bluecodegames.com/menu",
            Response.Listener
            {
                    response ->
                val datas = Gson().fromJson(JSONObject(response).get("data").toString(), Array<Menu>::class.java)
                if (page_name.text.toString() == "Entrées") {
                    var adapterDishesActivity: AdapterDishesActivity = AdapterDishesActivity(0, datas);
                    recyclerView.adapter = adapterDishesActivity

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
                        intent.putExtra("page_name","Entrées")
                        startActivity(intent);
                    }
                }

                if (page_name.text.toString() == "Plats") {
                    var adapterDishesActivity: AdapterDishesActivity = AdapterDishesActivity(1, datas);
                    recyclerView.adapter = adapterDishesActivity

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
                        intent.putExtra("page_name","Plats")
                        startActivity(intent);
                    }
                }

                if (page_name.text.toString() == "Desserts") {
                    var adapterDishesActivity: AdapterDishesActivity = AdapterDishesActivity(2, datas);
                    recyclerView.adapter = adapterDishesActivity

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
                        intent.putExtra("page_name","Desserts")
                        startActivity(intent);
                    }
                }
                Log.i("test", datas.toString());
            },
            Response.ErrorListener
            {
                    error ->
                Log.i("This is the error", "Error :" + error.toString())
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/json"
            }

            override fun getBody(): ByteArray {
                val params2 = HashMap<String, String>()
                params2.put("id_shop","1" )
                return JSONObject(params2 as Map<*, *>).toString().toByteArray()
            }

        }
        mRequestQueue!!.add(mStringRequest!!)

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