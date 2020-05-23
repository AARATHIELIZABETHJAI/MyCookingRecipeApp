package com.example.tastebuds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tastebuds.databinding.RecyclerViewItemBinding
import java.net.URL

class MyAdapter(val recipeItemClickListener: OnRecipeItemClickListener) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    var recipeList = ArrayList<Result>()
    var oldRecipeList = ArrayList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recycler_view_item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(recipeList[position],recipeItemClickListener)
    }

    class MyViewHolder(
        val recyclerViewItemBinding: RecyclerViewItemBinding
    ) : RecyclerView.ViewHolder(recyclerViewItemBinding.root){

        fun bind(recipe : Result, recipeItemClickListener: OnRecipeItemClickListener) {
            recyclerViewItemBinding.recipeModel = recipe
            recyclerViewItemBinding.clickListener = recipeItemClickListener
            recyclerViewItemBinding.executePendingBindings()
        }
    }

    class OnRecipeItemClickListener(val clickListener: (recipeId : Int) -> Unit) {
        fun onClick(recipe : Result) = clickListener(recipe.id)
    }

    fun updateList(newList: ArrayList<Result>) {
        val oldList = this.recipeList
        this.recipeList = newList
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].id == newList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getOldListSize() = oldList.size
            override fun getNewListSize() = newList.size
        })

        diff.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }
}