package com.example.tastebuds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(var recipeList:ArrayList<Result>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(val itemview: View) : RecyclerView.ViewHolder(itemview)
    {
        var recipeImage = itemview.findViewById(R.id.recipeImage) as ImageView
        var recipeName = itemview.findViewById(R.id.recipeName) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return recipeList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.recipeName.setText(recipeList.get(position).toString())
    }
}