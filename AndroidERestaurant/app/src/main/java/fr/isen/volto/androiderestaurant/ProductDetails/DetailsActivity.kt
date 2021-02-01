package fr.isen.volto.androiderestaurant.ProductDetails

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import fr.isen.volto.androiderestaurant.*
import fr.isen.volto.androiderestaurant.Cart.CartActivity
import fr.isen.volto.androiderestaurant.Dishes.DishesActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter


class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_details_activity)

        val dish = intent.getSerializableExtra("dish") as Item

        dish.getAllPictures()?.let {
            var detailPager: ViewPager2 = findViewById(R.id.detailPager);
            detailPager.adapter = DetailViewAdapter(this, it);
        }

        val product_name = findViewById<TextView>(R.id.product_details_name)
        val product_ingredients = findViewById<TextView>(R.id.product_details_ingredients)
        val product_quantity = findViewById<TextView>(R.id.product_details_quantity)
        val product_btn_send = findViewById<Button>(R.id.product_details_send)
        val product_btn_add = findViewById<FloatingActionButton>(R.id.addQuantity)
        val product_btn_minus = findViewById<FloatingActionButton>(R.id.minusQuantity)

        product_name.text = dish.name_fr;
        product_ingredients.text = dish.getFormattedIngredients();
        product_btn_send.text = "TOTAL " + dish.getFormattedPrice();

        product_btn_add.setOnClickListener {
            val quantity: Long? = product_quantity.text.toString().toLongOrNull()
            if (quantity != null && quantity < 99) {
                product_quantity.text = (quantity + 1L).toString();
                product_btn_send.text = "TOTAL " + dish.getPrice()*(quantity + 1L) + "€";
            }
        }

        product_btn_minus.setOnClickListener {
            val quantity: Long? = product_quantity.text.toString().toLongOrNull()
            if (quantity != null && quantity > 1) {
                product_quantity.text = (quantity - 1L).toString();
                product_btn_send.text = "TOTAL " + dish.getPrice()*(quantity - 1L) + "€";
            }
        }

        product_btn_send.setOnClickListener {
            // Convert JsonObject to String Format

            AlertDialog.Builder(this)
                    .setTitle("Opération sur votre panier")
                    .setMessage("Ajouter "+ product_quantity.text.toString() + " " + dish.name_fr +" à votre panier ?") // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, DialogInterface.OnClickListener { dialog, which ->

                        var  orders: Array<Order> = try {
                            Gson().fromJson(FileReader(this.filesDir.toString()+"/"+"cart_informations.json"), Array<Order>::class.java)
                        } catch(e:Exception) {
                            emptyArray()
                        }

                        val cartInformations = JSONArray()
                        var isAlreadyIn = false;
                        if (orders.isNotEmpty())
                        {
                            orders.forEach {
                                val product_infos: JSONObject  = JSONObject () // Define the File Path and its Name
                                product_infos.put("product_category",it.product_category)
                                product_infos.put("product_name",it.product_name)
                                product_infos.put("product_price",it.product_price)
                                if(
                                        dish.categ_name_fr == it.product_category &&
                                        dish.name_fr == it.product_name &&
                                        dish.getPrice().toFloat() == it.product_price
                                ) {
                                    isAlreadyIn = true
                                    product_infos.put("product_quantity", it.product_quantity + product_quantity.text.toString().toUInt())
                                }
                                else
                                    product_infos.put("product_quantity", it.product_quantity)
                                cartInformations.put(product_infos)
                            }
                        }

                        if(!isAlreadyIn)
                        {
                            val product_infos: JSONObject  = JSONObject () // Define the File Path and its Name
                            product_infos.put("product_category",dish.categ_name_fr)
                            product_infos.put("product_name",dish.name_fr)
                            product_infos.put("product_price",dish.getPrice())
                            product_infos.put("product_quantity", product_quantity.text.toString().toLongOrNull())
                            cartInformations.put(product_infos)
                        }

                        val file = File(this.filesDir, "cart_informations.json")
                        Log.i("path",this.filesDir.toString())
                        val fileWriter = FileWriter(file,false)
                        val bufferedWriter = BufferedWriter(fileWriter)
                        bufferedWriter.write(cartInformations.toString())
                        bufferedWriter.close()

                        var intent = Intent(this, DishesActivity::class.java);
                        intent.putExtra("page_name",dish.categ_name_fr)
                        startActivity(intent);
                        Toast.makeText(this,"Article ajouté au panier",Toast.LENGTH_SHORT).show();
                    }) // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()

        }

    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.my_options_menu, menu)
        return true
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