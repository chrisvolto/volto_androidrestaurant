package fr.isen.volto.androiderestaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView;
import org.w3c.dom.Text

public class MyAdapter(val s1: Array<String>, var s2: Array<String>, var s3: Array<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.product_item, parent, false)

        return(MyViewHolder(contactView))
    }

    override fun getItemCount(): Int = s1.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = s1[position]
        holder.description.text = s2[position]
        holder.ingredients.text = s3[position]
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView = itemView.findViewById(R.id.name);
        var description: TextView = itemView.findViewById(R.id.description);
        var ingredients: TextView = itemView.findViewById(R.id.ingredients);
    }
}