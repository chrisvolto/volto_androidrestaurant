package fr.isen.volto.androiderestaurant

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.squareup.picasso.Picasso
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

public class AdapterCartActivity(val categoryId: Int, val datas: Array<Order>) : RecyclerView.Adapter<AdapterCartActivity.MyViewHolder>() {
    private lateinit var context: Context;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCartActivity.MyViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)

        val contactView = inflater.inflate(R.layout.cart_item, parent, false)
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


        holder.productUnitPrice.text = datas[position].product_price.toString()
        holder.quantity.text = datas[position].product_quantity.toString()
        holder.productPrice.text = (datas[position].product_price * datas[position].product_quantity.toFloat()).toString()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView = itemView.findViewById(R.id.cart_product_name);
        var quantity: TextView = itemView.findViewById(R.id.product_cart_quantity);
        var btnAdd: FloatingActionButton = itemView.findViewById(R.id.cartAddQuantity);
        var btnMinus: FloatingActionButton = itemView.findViewById(R.id.cartMinusQuantity);
        var productUnitPrice: TextView = itemView.findViewById(R.id.cart_product_unit_price);
        var productPrice: TextView = itemView.findViewById(R.id.cart_product_price);
        var imagePreview: ImageView = itemView.findViewById(R.id.cart_product_preview);
    }

    override fun getItemCount(): Int = datas.size
}