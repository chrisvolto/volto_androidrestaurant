package fr.isen.volto.androiderestaurant

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject


class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)

        var btn = findViewById<Button>(R.id.btn_details);

        btn.setOnClickListener {
            //getDatas()
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        Log.i("debug", " destroyed") // log the destroy cycle
    }
}