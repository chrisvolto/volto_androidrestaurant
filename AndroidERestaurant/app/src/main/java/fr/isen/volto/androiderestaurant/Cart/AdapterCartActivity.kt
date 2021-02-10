package fr.isen.volto.androiderestaurant.Cart

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
import java.io.File

class AdapterCartActivity(private var datas: Array<ProductOrder>, val callback: (arr: Array<ProductOrder>) -> Unit) : RecyclerView.Adapter<AdapterCartActivity.MyViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCartActivity.MyViewHolder {
        context = parent.context
        val contactView = CartProductItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return(MyViewHolder(contactView))
    }

    inner class MyViewHolder(binding: CartProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var name: TextView = binding.cartProductName
        var quantity: TextView = binding.productCartQuantity
        var btnAdd: FloatingActionButton = binding.cartAddQuantity
        var btnMinus: FloatingActionButton = binding.cartMinusQuantity
        var btnDelete: Button = binding.deleteArticle
        var totalPrice: TextView = binding.cartProductPrice
        var imagePreview: ImageView = binding.cartProductPreview
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = datas[position].product.name_fr
        val picasso = Picasso.get()
        val img = datas[position].product.images[0]
        (if (img.isBlank()) picasso.load(R.drawable.missing_image) else picasso.load(img))
                .resize(300, 300)
                .centerCrop()
                .into(holder.imagePreview)
        holder.quantity.text = datas[position].quantity.toString()
        holder.totalPrice.text = "Prix: " + (datas[position].quantity.toFloat()* datas[position].product.getPrice()).toString() + "€"

        holder.btnAdd.setOnClickListener {
            holder.quantity.text.toString().toLong().let {
                if (it < 99) {
                    datas[position].quantity = (it + 1L).toULong()
                    callback(datas)
                    holder.quantity.text = (it + 1L).toString()
                    holder.totalPrice.text = "TOTAL " + datas[position].product.getPrice()*(it + 1L) + "€"
                    modifyCart(ProductOrder(datas[position].product,holder.quantity.text.toString().toULong()),CartAction.MODIFY)
                }
            }
        }
        holder.btnMinus.setOnClickListener {
            holder.quantity.text.toString().toLong().let {
                if (it > 1) {
                    datas[position].quantity = (it - 1L).toULong()
                    callback(datas)
                    holder.quantity.text = (it - 1L).toString()
                    holder.totalPrice.text = "TOTAL " + datas[position].product.getPrice()*(it - 1L) + "€"
                    modifyCart(ProductOrder(datas[position].product,holder.quantity.text.toString().toULong()),CartAction.MODIFY)
                }
            }
        }
        holder.btnDelete.setOnClickListener {
            Log.i("tag", datas[position].quantity.toString()+"x"+datas[position].product.getPrice().toString()+(datas[position].quantity.toFloat() * datas[position].product.getPrice()).times(-1).toString())
            modifyCart(ProductOrder(datas[position].product,holder.quantity.text.toString().toULong()),CartAction.DELETE)
            val cartList = datas.toMutableList()
            cartList.remove(datas[position])
            datas = cartList.toTypedArray()
            callback(datas)
            this.notifyItemRemoved(position)
            this.notifyItemRangeChanged(position, datas.size)
        }
    }

    enum class CartAction{ MODIFY, DELETE }
    private fun modifyCart(productOrder: ProductOrder, action: CartAction){
        val file = File(context.cacheDir.absolutePath+"/cart.json")
        var cart = GsonBuilder().create().fromJson(file.readText(), Array<ProductOrder>::class.java)

        cart.firstOrNull { it.product.name_fr == productOrder.product.name_fr }
                ?.let {
                    if (action == CartAction.MODIFY)
                        it.quantity = productOrder.quantity
                    else if(action == CartAction.DELETE) {
                        val cartList = cart.toMutableList()
                        cartList.remove(it)
                        cart = cartList.toTypedArray()
                    }
                }
        file.writeText(GsonBuilder().create().toJson(cart))
    }

    override fun getItemCount(): Int = datas.size
}