package fr.isen.volto.androiderestaurant.Cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import fr.isen.volto.androiderestaurant.Category
import fr.isen.volto.androiderestaurant.OrderSummary.OrderActivity
import fr.isen.volto.androiderestaurant.ProductOrder
import fr.isen.volto.androiderestaurant.User
import fr.isen.volto.androiderestaurant.account.SignInActivity
import fr.isen.volto.androiderestaurant.databinding.CartActivityBinding
import fr.isen.volto.androiderestaurant.home.HomeActivity
import java.io.File


class CartActivity : AppCompatActivity() {
    private lateinit var binding: CartActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CartActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val categories = intent.getSerializableExtra("categories") as? Array<Category>

        val file = File(cacheDir.absolutePath+"/cart.json")
        if (file.exists()) {
            var cart = GsonBuilder().create().fromJson(file.readText(), Array<ProductOrder>::class.java)
            if(cart.isNotEmpty()){
                binding.cartBuyCart.visibility = View.VISIBLE
                binding.anyArticleCart.visibility = View.INVISIBLE

                var recyclerView = binding.recyclerViewCart
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = AdapterCartActivity(cart) { arr ->
                    cart = arr
                    var total = 0F
                    cart.forEach {
                        total += it.quantity.toFloat()*it.product.getPrice()
                    }
                    binding.cartBuyCart.text = total.toString() + "€"
                    if( total == 0F) {
                        binding.cartBuyCart.visibility = View.INVISIBLE
                        binding.anyArticleCart.visibility = View.VISIBLE
                    }else{
                        binding.cartBuyCart.visibility = View.VISIBLE
                        binding.anyArticleCart.visibility = View.INVISIBLE
                    }
                }

                var total = 0F
                cart.forEach {
                    total += it.quantity.toFloat()*it.product.getPrice()
                }
                binding.cartBuyCart.text = total.toString() + "€"
                binding.cartBuyCart.setOnClickListener {

                    val file = File(cacheDir.absolutePath+"/user.json")
                    if (file.exists()) {
                        val intent = Intent(this, OrderActivity::class.java)
                        intent.putExtra("user", GsonBuilder().create().fromJson(file.readText(), User::class.java))
                        intent.putExtra("cart", cart)
                        intent.putExtra("categories", categories)
                        startActivity(intent)
                    }
                    else{
                        startActivity(Intent(this, SignInActivity::class.java))
                    }
                }
            }
        }

        binding.cartHomeCart.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("categories", categories)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("debug", " destroyed") // log the destroy cycle
    }
}