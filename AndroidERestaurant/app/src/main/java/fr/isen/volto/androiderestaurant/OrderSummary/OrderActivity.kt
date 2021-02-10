package fr.isen.volto.androiderestaurant.OrderSummary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.volto.androiderestaurant.APIManager.APIManager
import fr.isen.volto.androiderestaurant.Cart.AdapterCartActivity
import fr.isen.volto.androiderestaurant.Category
import fr.isen.volto.androiderestaurant.ProductOrder
import fr.isen.volto.androiderestaurant.R
import fr.isen.volto.androiderestaurant.User
import fr.isen.volto.androiderestaurant.account.SignInActivity
import fr.isen.volto.androiderestaurant.databinding.CartActivityBinding
import fr.isen.volto.androiderestaurant.databinding.OrderActivityBinding
import fr.isen.volto.androiderestaurant.home.HomeActivity
import org.json.JSONObject
import java.io.File
import java.io.FileReader

public class OrderActivity : AppCompatActivity() {
        private lateinit var binding: OrderActivityBinding
        private lateinit var cart: Array<ProductOrder>

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding = OrderActivityBinding.inflate(layoutInflater)
                setContentView(binding.root)
                val categories = intent.getSerializableExtra("categories") as? Array<Category>

                val user = intent.getSerializableExtra("user") as User

                val file = File(cacheDir.absolutePath+"/cart.json")
                if (file.exists()) {
                        cart = GsonBuilder().create().fromJson(file.readText(), Array<ProductOrder>::class.java)
                        if(cart.isNotEmpty()){

                                var recyclerView = binding.recyclerViewOrder
                                recyclerView.layoutManager = LinearLayoutManager(this);
                                recyclerView.adapter = AdapterCartOrder(cart)

                                var total = 0F
                                cart.forEach {
                                        total += it.quantity.toFloat()*it.product.getPrice()
                                }
                                binding.totalpriceOrder.text = total.toString()
                        }
                }

                binding.backHomeOrder.setOnClickListener {
                        val intent = Intent(this, HomeActivity::class.java);
                        intent.putExtra("categories", categories)
                        startActivity(intent);
                }

                binding.sendFormOrder.setOnClickListener {
                        val mReqQueue = Volley.newRequestQueue(this)
                        val apiManager = APIManager(mReqQueue) {
                                mReqQueue.cancelAll {true}
                                if( it is User)
                                {

                                        Toast.makeText(this,"Votre commande a été prise en compte.", Toast.LENGTH_SHORT).show();

                                        val intent = Intent(this, HomeActivity::class.java);
                                        intent.putExtra("categories", categories)
                                        startActivity(intent);
                                }else if(it is String){
                                        Toast.makeText(this,it as String, Toast.LENGTH_SHORT).show();
                                }else{
                                        Toast.makeText(this,"Une erreur est survenue.", Toast.LENGTH_SHORT).show();
                                }
                        }

                        apiManager.makeOrder(user,GsonBuilder().create().toJson(cart))
                }

        }

        override fun onDestroy() {
                super.onDestroy()
                Log.i("debug", " destroyed") // log the destroy cycle
        }
}
