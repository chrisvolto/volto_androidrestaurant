package fr.isen.volto.androiderestaurant.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import fr.isen.volto.androiderestaurant.Cart.CartActivity
import fr.isen.volto.androiderestaurant.Category
import fr.isen.volto.androiderestaurant.products.ProductsActivity
import fr.isen.volto.androiderestaurant.ProductOrder
import fr.isen.volto.androiderestaurant.R
import fr.isen.volto.androiderestaurant.databinding.HomeActivityBinding
import java.io.File
import kotlin.math.min


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: HomeActivityBinding
    private var categories: Array<Category>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categories = intent.getSerializableExtra("categories") as? Array<Category>

        val intent = Intent(this, ProductsActivity::class.java);
        intent.putExtra("categories", categories)
        binding.startersButtonHome.setOnClickListener {
            intent.putExtra("page_name","Entrées")
            startActivity(intent);
        }

        binding.dishesButtonHome.setOnClickListener {
            intent.putExtra("page_name","Plats")
            startActivity(intent);
        }

        binding.dessertsButtonHome.setOnClickListener {
            intent.putExtra("page_name","Desserts")
            startActivity(intent);
        }
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.my_options_menu, menu);
        menu?.findItem(R.id.action_cart)?.let {
            val actionView: View = it.actionView
            val textCartItemCount = actionView.findViewById<View>(R.id.cart_badge) as TextView
            val file = File(cacheDir.absolutePath+"/cart.json")
            if (file.exists() && GsonBuilder().create().fromJson(file.readText(), Array<ProductOrder>::class.java).isNotEmpty()) {
                val productsNumber = GsonBuilder().create().fromJson(file.readText(), Array<ProductOrder>::class.java).size
                textCartItemCount.text = min(productsNumber, 99).toString();
                textCartItemCount.visibility = View.VISIBLE;
            }
            else{
                textCartItemCount.visibility = View.GONE;
            }

            actionView.setOnClickListener {_ ->
                onOptionsItemSelected(it)
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(this, CartActivity::class.java).putExtra("categories", categories));
        return true;
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.i("debug", " destroyed") // log the destroy cycle
    }
}