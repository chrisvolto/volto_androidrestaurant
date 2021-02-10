package fr.isen.volto.androiderestaurant.products

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.volto.androiderestaurant.Cart.CartActivity
import fr.isen.volto.androiderestaurant.Category
import fr.isen.volto.androiderestaurant.home.HomeActivity
import fr.isen.volto.androiderestaurant.ProductOrder
import fr.isen.volto.androiderestaurant.R
import fr.isen.volto.androiderestaurant.databinding.HomeActivityBinding
import fr.isen.volto.androiderestaurant.databinding.ProductsActivityBinding
import org.json.JSONObject
import java.io.File
import java.io.FileReader
import kotlin.math.min


class ProductsActivity : AppCompatActivity() {
    private lateinit var binding: ProductsActivityBinding
    private var categories: Array<Category>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pageNameProducts.text = intent.getStringExtra("page_name")
        categories = intent.getSerializableExtra("categories") as? Array<Category>
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(this)

        when(binding.pageNameProducts.text.toString()){
            "Entrées" -> {
                binding.recyclerViewProducts.adapter = categories?.let { ProductsAdapterActivity(0, it) }
                binding.startersButtonProducts.text = "Accueil"

                binding.startersButtonProducts.setOnClickListener {startActivity(Intent(this, HomeActivity::class.java).putExtra("categories", categories))}
                binding.dishesButtonProducts.setOnClickListener {startActivity(Intent(this, ProductsActivity::class.java).putExtra("page_name","Plats").putExtra("categories", categories))}
                binding.dessertsButtonProducts.setOnClickListener {startActivity(Intent(this, ProductsActivity::class.java).putExtra("page_name","Desserts").putExtra("categories", categories))}
            }
            "Plats" -> {
                binding.recyclerViewProducts.adapter = categories?.let { ProductsAdapterActivity(1, it) }
                binding.dishesButtonProducts.text = "Accueil"

                binding.startersButtonProducts.setOnClickListener {startActivity(Intent(this, ProductsActivity::class.java).putExtra("page_name","Entrées").putExtra("categories", categories))}
                binding.dishesButtonProducts.setOnClickListener {startActivity(Intent(this, HomeActivity::class.java).putExtra("categories", categories))}
                binding.dessertsButtonProducts.setOnClickListener {startActivity(Intent(this, ProductsActivity::class.java).putExtra("page_name","Desserts").putExtra("categories", categories))}
            }
            "Desserts" -> {
                binding.recyclerViewProducts.adapter = categories?.let { ProductsAdapterActivity(2, it) }
                binding.dessertsButtonProducts.text = "Accueil"

                binding.startersButtonProducts.setOnClickListener {startActivity(Intent(this, ProductsActivity::class.java).putExtra("page_name","Entrées").putExtra("categories", categories))}
                binding.dishesButtonProducts.setOnClickListener {startActivity(Intent(this, ProductsActivity::class.java).putExtra("page_name","Plats").putExtra("categories", categories))}
                binding.dessertsButtonProducts.setOnClickListener {startActivity(Intent(this, HomeActivity::class.java).putExtra("categories", categories))}
            }
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