package fr.isen.volto.androiderestaurant.ProductDetails

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.volto.androiderestaurant.*
import fr.isen.volto.androiderestaurant.Cart.CartActivity
import fr.isen.volto.androiderestaurant.databinding.ProductDetailsActivityBinding
import fr.isen.volto.androiderestaurant.products.ProductsActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import kotlin.math.min


class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ProductDetailsActivityBinding
    private var categories: Array<Category>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductDetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categories = intent.getSerializableExtra("categories") as? Array<Category>
        val product = intent.getSerializableExtra("product") as Product

        product.getAllPictures()?.let {
            binding.detailPager.adapter = DetailViewAdapter(this, it);
        }
        binding.productDetailsName.text = product.name_fr;
        binding.productDetailsIngredients.text = product.getFormattedIngredients();
        binding.productDetailsSend.text = "TOTAL " + product.getFormattedPrice();

        binding.addQuantity.setOnClickListener {
            binding.productDetailsQuantity.text.toString().toLong().let {
                if (it < 99) {
                    binding.productDetailsQuantity.text = (it + 1L).toString();
                    binding.productDetailsSend.text = "TOTAL " + product.getPrice()*(it + 1L) + "€";
                }
            }
        }

        binding.minusQuantity.setOnClickListener {
            binding.productDetailsQuantity.text.toString().toLong().let {
                if (it > 1) {
                    binding.productDetailsQuantity.text = (it - 1L).toString();
                    binding.productDetailsSend.text = "TOTAL " + product.getPrice()*(it - 1L) + "€";
                }
            }
        }

        binding.productDetailsSend.setOnClickListener {
            AlertDialog.Builder(this)
                    .setTitle("Opération sur votre panier")
                    .setMessage("Ajouter "+product.name_fr+"x"+binding.productDetailsQuantity.text.toString()+" à votre panier ?")
                    .setPositiveButton(android.R.string.yes, DialogInterface.OnClickListener { _, _ ->
                        val file = File(cacheDir.absolutePath+"/cart.json")
                        var cart: Array<ProductOrder>
                        if (file.exists()){
                            cart = GsonBuilder().create().fromJson(file.readText(), Array<ProductOrder>::class.java)
                            cart.firstOrNull { it.product.name_fr == product.name_fr }
                                ?.let {
                                    it.quantity += binding.productDetailsQuantity.text.toString().toULong()
                                }
                                ?: run {
                                    cart = cart.plus(ProductOrder(product,binding.productDetailsQuantity.text.toString().toULong()))
                                }
                        }
                        else cart = arrayOf(ProductOrder(product,binding.productDetailsQuantity.text.toString().toULong()))
                        file.writeText(GsonBuilder().create().toJson(cart))

                        var intent = Intent(this, ProductsActivity::class.java);
                        intent.putExtra("page_name",product.categ_name_fr)
                        intent.putExtra("categories", categories)
                        startActivity(intent);
                        Toast.makeText(this,"Article ajouté au panier",Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
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