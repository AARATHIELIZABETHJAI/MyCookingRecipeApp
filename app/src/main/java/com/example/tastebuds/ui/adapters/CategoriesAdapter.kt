package com.example.tastebuds.ui.adapters

import android.content.Context
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tastebuds.R
import com.example.tastebuds.databinding.CategoryRecyclerviewItemBinding

var categories: Array<String> = arrayOf("Salads", "Starters", "Desserts","Soups","Soups","Soups","Soups","Soups")
class CategoriesAdapter(val ctx:Context,val categoryItemClickListener: OnCategoryItemClickListener) : RecyclerView.Adapter<CategoriesAdapter.MyViewHolder>() {

    class MyViewHolder(val categoryRecyclerviewItemBinding:CategoryRecyclerviewItemBinding
    ) : RecyclerView.ViewHolder(categoryRecyclerviewItemBinding.root){

        fun bind(categoryName:String,resourceInt: Int, categoryItemClickListener: OnCategoryItemClickListener) {
            categoryRecyclerviewItemBinding.categoryName = categoryName
            categoryRecyclerviewItemBinding.resourceId = resourceInt
            categoryRecyclerviewItemBinding.clickListener = categoryItemClickListener
            categoryRecyclerviewItemBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.category_recyclerview_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val imgs: TypedArray = ctx.resources.obtainTypedArray(R.array.categoryDrawable)
        val resourceInt = imgs.getResourceId(position, -1)
        holder.bind(categories[position],resourceInt,categoryItemClickListener)
        imgs.recycle()
    }

    class OnCategoryItemClickListener(val clickListener: (categoryName: String) -> Unit) {
        fun onClick(categoryName : String) = clickListener(categoryName)
    }
}