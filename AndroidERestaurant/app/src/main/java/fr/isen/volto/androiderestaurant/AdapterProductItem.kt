package fr.isen.volto.androiderestaurant

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

public class AdapterProductItem(val categoryId: Int, val datas: Array<Menu>) : RecyclerView.Adapter<AdapterProductItem.MyViewHolder>() {
    private lateinit var context: Context;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterProductItem.MyViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)

        val contactView = inflater.inflate(R.layout.product_item, parent, false)
        return(MyViewHolder(contactView))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = datas[categoryId].items[position].name_fr
        Log.i("info", datas[categoryId].items[position].toString())
        var ingredients: String = "";
        val iIngredients: Iterator<Ingredient> = datas[categoryId].items[position].ingredients.iterator()
        while (iIngredients.hasNext())
        {
            ingredients = ingredients.plus(iIngredients.next().name_fr)

            if (iIngredients.hasNext()) {
                ingredients = ingredients.plus(", ")
            }
        }
        holder.ingredients.text = ingredients
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
        }


        holder.price.text = datas[categoryId].items[position].prices[0].price.toString();

        holder.btnSeeMore.setOnClickListener {
            var intent = Intent(context, DetailsActivity::class.java);
            context.startActivity(intent)
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView = itemView.findViewById(R.id.product_name);
        var ingredients: TextView = itemView.findViewById(R.id.product_ingredients);
        var price: TextView = itemView.findViewById(R.id.product_price);
        var btnSeeMore: ImageView = itemView.findViewById(R.id.btn_see_more);
        var imagePreview: ImageView = itemView.findViewById(R.id.product_preview);
    }

    override fun getItemCount(): Int = datas[categoryId].items.size
}