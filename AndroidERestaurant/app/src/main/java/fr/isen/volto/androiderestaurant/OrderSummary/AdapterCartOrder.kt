
package fr.isen.volto.androiderestaurant.OrderSummary

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import fr.isen.volto.androiderestaurant.ProductOrder
import fr.isen.volto.androiderestaurant.R
import fr.isen.volto.androiderestaurant.databinding.CartProductItemBinding
import fr.isen.volto.androiderestaurant.databinding.OrderActivityBinding
import fr.isen.volto.androiderestaurant.databinding.OrderItemBinding
import java.io.File

class AdapterCartOrder(private var datas: Array<ProductOrder>) : RecyclerView.Adapter<AdapterCartOrder.MyViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val contactView = OrderItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return(MyViewHolder(contactView))
    }

    inner class MyViewHolder(binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var name: TextView = binding.nameOrder
        var quantity: TextView = binding.quantityOrder
        var unitPrice: TextView = binding.unitPriceOrder
        var price: TextView = binding.priceOrder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = datas[position].product.name_fr
        holder.quantity.text = datas[position].quantity.toString()
        holder.price.text = (datas[position].quantity.toFloat()* datas[position].product.getPrice()).toString()
        holder.unitPrice.text = datas[position].product.getPrice().toString()
    }

    override fun getItemCount(): Int = datas.size
}