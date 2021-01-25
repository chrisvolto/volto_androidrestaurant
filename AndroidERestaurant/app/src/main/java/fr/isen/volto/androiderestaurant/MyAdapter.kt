package fr.isen.volto.androiderestaurant

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter(private val categories: List<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}