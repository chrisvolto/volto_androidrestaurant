package fr.isen.volto.androiderestaurant.Cart

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import fr.isen.volto.androiderestaurant.Home.RestaurantActivity
import fr.isen.volto.androiderestaurant.Order
import fr.isen.volto.androiderestaurant.R
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter

public class AdapterCartActivity(val categoryId: Int, val datas: Array<Order>) : RecyclerView.Adapter<AdapterCartActivity.MyViewHolder>() {
    private lateinit var context: Context;

    enum class ItemCartAction {
        DELETE,
        ADD,
        MINUS
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)

        val contactView = inflater.inflate(R.layout.cart_product_item, parent, false)
        return(MyViewHolder(contactView))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = datas[position].product_name
        /*
        if(datas[categoryId].items[position].images[0] != "")
        {
            Picasso.get()
                    .load(datas[categoryId].items[position].images[0])
                    .resize(300, 300)
                    .error(R.drawable.missing_image)
                    .centerCrop()
                    .into(holder.imagePreview);
        }
        else
        {
            Picasso.get()
                    .load(R.drawable.missing_image)
                    .resize(300, 300)
                    .centerCrop()
                    .into(holder.imagePreview);
        }*/


        //holder.productUnitPrice.text =  datas[position].product_price.toString() + "€/u"
        holder.quantity.text = datas[position].product_quantity.toString()
        holder.productPrice.text = "Prix: " + (datas[position].product_price * datas[position].product_quantity.toFloat()).toString() + "€"

        holder.btnAdd.setOnClickListener {
            var quantity: Long? = holder.quantity.text.toString().toLongOrNull()
            if (quantity != null && quantity < 99) {
                quantity++
                holder.quantity.text = quantity.toString();
                holder.productPrice.text = "Prix: " + (datas[position].product_price * quantity).toString() + "€"
                modifyCart(
                        Order(
                                datas[position].product_category,
                                datas[position].product_name,
                                datas[position].product_price,
                                quantity.toULong()
                        ),
                        ItemCartAction.ADD
                )
            }
        }

        holder.btnMinus.setOnClickListener {
            var quantity: Long? = holder.quantity.text.toString().toLongOrNull()
            if (quantity != null && quantity > 1) {
                quantity--
                holder.quantity.text = quantity.toString();
                holder.productPrice.text = "Prix: " + (datas[position].product_price * quantity).toString() + "€"
                modifyCart(
                        Order(
                                datas[position].product_category,
                                datas[position].product_name,
                                datas[position].product_price,
                                quantity.toULong()
                        ),
                        ItemCartAction.MINUS
                )
            }
        }

        holder.btnDelete.setOnClickListener {
            modifyCart(
                    Order(
                            datas[position].product_category,
                            datas[position].product_name,
                            datas[position].product_price,
                            0u
                    ),
                    ItemCartAction.DELETE
            )
            val intent = Intent(context, CartActivity::class.java);
            context.startActivity(intent);
        }
    }

    fun modifyCart(dish: Order, action: ItemCartAction){
        var  orders: Array<Order>;
        try {
            orders = Gson().fromJson(FileReader(context.filesDir.toString()+"/"+"cart_informations.json"), Array<Order>::class.java)
        } catch(e:java.io.FileNotFoundException)
        {
            orders = emptyArray()
        }

        val cartInformations = JSONArray()
        if (orders.isNotEmpty())
        {
            orders.forEach {

                val product_infos: JSONObject = JSONObject() // Define the File Path and its Name
                product_infos.put("product_category", it.product_category)
                product_infos.put("product_name", it.product_name)
                product_infos.put("product_price", it.product_price)
                if(
                    dish.product_category == it.product_category &&
                    dish.product_name == it.product_name &&
                    dish.product_price == it.product_price
                ) {
                    when(action)
                    {
                        ItemCartAction.ADD -> product_infos.put("product_quantity", dish.product_quantity)
                        ItemCartAction.MINUS -> product_infos.put("product_quantity", dish.product_quantity)
                        else -> product_infos.put("product_quantity", it.product_quantity)
                    }
                }
                else
                {
                    product_infos.put("product_quantity", it.product_quantity)
                }

                if(
                    !(dish.product_category == it.product_category &&
                    dish.product_name == it.product_name &&
                    dish.product_price == it.product_price &&
                    action == ItemCartAction.DELETE)
                ) {
                    cartInformations.put(product_infos)
                }
            }
        }

        val file = File(context.filesDir, "cart_informations.json")

        val fileWriter = FileWriter(file,false)
        val bufferedWriter = BufferedWriter(fileWriter)
        bufferedWriter.write(cartInformations.toString())
        bufferedWriter.close()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView = itemView.findViewById(R.id.cart_product_name);
        var quantity: TextView = itemView.findViewById(R.id.product_cart_quantity);
        var btnAdd: FloatingActionButton = itemView.findViewById(R.id.cartAddQuantity);
        var btnMinus: FloatingActionButton = itemView.findViewById(R.id.cartMinusQuantity);
        var btnDelete: Button = itemView.findViewById(R.id.delete_article);
        var productPrice: TextView = itemView.findViewById(R.id.cart_product_price);
        var imagePreview: ImageView = itemView.findViewById(R.id.cart_product_preview);
    }

    override fun getItemCount(): Int = datas.size
}