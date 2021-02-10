package fr.isen.volto.androiderestaurant.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.volto.androiderestaurant.APIManager.APIManager
import fr.isen.volto.androiderestaurant.OrderSummary.OrderActivity
import fr.isen.volto.androiderestaurant.R
import fr.isen.volto.androiderestaurant.User
import fr.isen.volto.androiderestaurant.databinding.LoginActivityBinding
import fr.isen.volto.androiderestaurant.databinding.SigninActivityBinding
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

public class SignInActivity : AppCompatActivity() {
        private lateinit var binding: SigninActivityBinding
        private lateinit var mReqQueue: RequestQueue
        private lateinit var apiManager: APIManager

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding = SigninActivityBinding.inflate(layoutInflater)
                setContentView(binding.root)
                mReqQueue = Volley.newRequestQueue(this)
                apiManager = APIManager(mReqQueue) {
                        mReqQueue.cancelAll {true}
                        Log.i("intent", "rep")
                        if( it is User)
                        {
                                val file = File(this.cacheDir, "user.json")
                                file.writeText(GsonBuilder().create().toJson(it))

                                val intent = Intent(this, OrderActivity::class.java);
                                intent.putExtra("user", it);
                                startActivity(intent);
                        }else if(it is String){
                                Toast.makeText(this,it as String, Toast.LENGTH_SHORT).show();
                        }else{
                                Toast.makeText(this,"Une erreur est survenue.", Toast.LENGTH_SHORT).show();
                        }
                }

                binding.firstNameSignin.error = "Must enter your first name"
                binding.lastNameSignin.error = "Must enter your last name"
                binding.emailSignin.error = "Must enter your email"
                binding.addressSignin.error = "Must enter your address"
                binding.passwordSignin.error = "Must enter your password"

                binding.switchLoginSignin.setOnClickListener {
                        val intent = Intent(this, LogInActivity::class.java);
                        startActivity(intent);
                }

                binding.sendFormSignin.setOnClickListener {
                        apiManager.register(
                                binding.firstNameSignin.text.toString(),
                                binding.lastNameSignin.text.toString(),
                                binding.addressSignin.text.toString(),
                                binding.emailSignin.text.toString(),
                                binding.passwordSignin.text.toString()
                        )
                }

        }

        override fun onDestroy() {
                super.onDestroy()
                Log.i("debug", " destroyed") // log the destroy cycle
        }
}
