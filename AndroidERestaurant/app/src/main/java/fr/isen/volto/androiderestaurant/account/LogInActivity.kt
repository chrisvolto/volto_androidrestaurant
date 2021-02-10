package fr.isen.volto.androiderestaurant.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.volto.androiderestaurant.APIManager.APIManager
import fr.isen.volto.androiderestaurant.Category
import fr.isen.volto.androiderestaurant.OrderSummary.OrderActivity
import fr.isen.volto.androiderestaurant.R
import fr.isen.volto.androiderestaurant.User
import fr.isen.volto.androiderestaurant.databinding.LoginActivityBinding
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

public class LogInActivity : AppCompatActivity() {
        private lateinit var binding: LoginActivityBinding
        private lateinit var mReqQueue: RequestQueue
        private lateinit var apiManager: APIManager

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding = LoginActivityBinding.inflate(layoutInflater)
                setContentView(binding.root)
                mReqQueue = Volley.newRequestQueue(this)
                apiManager = APIManager(mReqQueue) {
                        mReqQueue.cancelAll {true}
                        Log.i("intent", "rep")
                        if( it is User)
                        {
                                val intent = Intent(this, OrderActivity::class.java);
                                intent.putExtra("user", it);
                                startActivity(intent);
                        }else{
                                Toast.makeText(this,"Mauvais indentifiants", Toast.LENGTH_SHORT).show();
                        }
                }

                binding.emailLogin.error = "Must enter your email"
                binding.passwordLogin.error = "Must enter your password"

                binding.switchCreateLogin.setOnClickListener {
                        val intent = Intent(this, SignInActivity::class.java);
                        startActivity(intent);
                }

                binding.sendFormLogin.setOnClickListener {
                        apiManager.login(binding.emailLogin.text.toString(), binding.passwordLogin.text.toString())
                }

        }

        override fun onDestroy() {
                super.onDestroy()
                Log.i("debug", " destroyed") // log the destroy cycle
        }
}
