package fr.isen.volto.androiderestaurant.products

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.volto.androiderestaurant.ProductDetails.DetailsActivity
import fr.isen.volto.androiderestaurant.Category
import fr.isen.volto.androiderestaurant.R
import fr.isen.volto.androiderestaurant.databinding.ProductsItemBinding

class ProductsAdapterActivity(private val categoryId: Int, private val datas: Array<Category>) : RecyclerView.Adapter<ProductsAdapterActivity.MyViewHolder>() {
    private lateinit var context: Context;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context

        val contactView = ProductsItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return(MyViewHolder(contactView))
    }

    inner class MyViewHolder(binding: ProductsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        var name: TextView = binding.cartProductName
        var ingredients: TextView = binding.productIngredients
        var price: TextView = binding.cartProductUnitPrice
        var btnSeeMore: ImageView = binding.btnSeeMore
        var imagePreview: ImageView = binding.cartProductPreview
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = datas[categoryId].items[position].name_fr

        holder.ingredients.text = datas[categoryId].items[position].getFormattedIngredients()
        val img = datas[categoryId].items[position].images[0]
        val picasso = Picasso.get()
        (if (img.isBlank()) picasso.load(R.drawable.missing_image) else picasso.load(img))
                .resize(300, 300)
                .centerCrop()
                .into(holder.imagePreview)

        holder.price.text = datas[categoryId].items[position].prices[0].price.toString();
        holder.btnSeeMore.setOnClickListener {
            context.startActivity(Intent(context, DetailsActivity::class.java).putExtra("categories", datas).putExtra("product", datas[categoryId].items[position]))
        }
    }

    override fun getItemCount(): Int = datas[categoryId].items.size
}