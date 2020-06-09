package com.example.tastebuds.ui.adapters

import android.content.Context
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tastebuds.R
import com.example.tastebuds.databinding.CuisinesRecyclerviewItemBinding

var cuisines: Array<String> =
    arrayOf("Continental", "Chinese", "Indian", "Italian", "Mediterranean")
var drawables: Array<String> = arrayOf(
    "R.drawable.continental",
    "R.drawable.chinese",
    "R.drawable.indian",
    "R.drawable.italian",
    "R.drawable.mediterranean"
)

class CuisinesAdapter(val ctx: Context, val cuisineItemClickListener: OnCuisineItemClickListener) :
    RecyclerView.Adapter<CuisinesAdapter.MyViewHolder>() {

    class MyViewHolder(
        val cuisinesRecyclerviewItemBinding: CuisinesRecyclerviewItemBinding
    ) : RecyclerView.ViewHolder(cuisinesRecyclerviewItemBinding.root) {

        fun bind(
            cuisineName: String,
            resourceInt: Int,
            cuisineItemClickListener: OnCuisineItemClickListener
        ) {
            cuisinesRecyclerviewItemBinding.cuisineName = cuisineName
            cuisinesRecyclerviewItemBinding.resourceId = resourceInt
            cuisinesRecyclerviewItemBinding.clickListener = cuisineItemClickListener
            cuisinesRecyclerviewItemBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.cuisines_recyclerview_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return cuisines.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val imgs:TypedArray = ctx.resources.obtainTypedArray(R.array.cuisineDrawable)
        val resourceInt = imgs.getResourceId(position, -1)
        holder.bind(cuisines[position], resourceInt, cuisineItemClickListener)
        imgs.recycle();
    }

    class OnCuisineItemClickListener(val clickListener: (cuisineName: String) -> Unit) {
        fun onClick(cuisine: String) = clickListener(cuisine)
    }
}