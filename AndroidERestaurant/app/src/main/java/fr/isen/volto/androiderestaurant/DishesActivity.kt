package fr.isen.volto.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import org.json.JSONObject


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
                if (page_name.text.toString() == "Les entrées") {
                    var adapterProductItem: AdapterProductItem = AdapterProductItem(0,datas);
                    recyclerView.adapter = adapterProductItem

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
                    var adapterProductItem: AdapterProductItem = AdapterProductItem(1,datas);
                    recyclerView.adapter = adapterProductItem

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
                    var adapterProductItem: AdapterProductItem = AdapterProductItem(2,datas);
                    recyclerView.adapter = adapterProductItem

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

    override fun onDestroy() {
        super.onDestroy()
        Log.i("debug", " destroyed") // log the destroy cycle
    }
}