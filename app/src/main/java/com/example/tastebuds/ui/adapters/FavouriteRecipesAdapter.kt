package com.example.tastebuds.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tastebuds.R
import com.example.tastebuds.persistence.models.RecipeDetail
import com.example.tastebuds.databinding.FavouriteRecyclerviewItemBinding


class FavouriteRecipesAdapter(
    val recipeItemClickListener: OnRecipeItemClickListener,
    val onItemInsertListener: (RecipeDetail) -> Unit,
    val onItemDeleteListener: (RecipeDetail) -> Unit
) :
    RecyclerView.Adapter<FavouriteRecipesAdapter.AdapterViewHolder>() {
    var favouriteRecipeList = ArrayList<RecipeDetail>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AdapterViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.favourite_recyclerview_item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.bind(
            favouriteRecipeList[position],
            recipeItemClickListener,
            onItemInsertListener,
            onItemDeleteListener
        )
    }

    class AdapterViewHolder(
        val favouriteRecyclerviewItemBinding: FavouriteRecyclerviewItemBinding
    ) : RecyclerView.ViewHolder(favouriteRecyclerviewItemBinding.root) {

        fun bind(
            recipeDetail: RecipeDetail, recipeItemClickListener: OnRecipeItemClickListener,
            onItemInsertListener: (RecipeDetail) -> Unit,
            onItemDeleteListener: (RecipeDetail) -> Unit
        ) {
            favouriteRecyclerviewItemBinding.toggleButton.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    buttonView.setButtonDrawable(R.drawable.ic_favorite_grey)
                    onItemDeleteListener(recipeDetail)
                }
            }
            favouriteRecyclerviewItemBinding.recipeDetailModel = recipeDetail
            favouriteRecyclerviewItemBinding.clickListener = recipeItemClickListener
            favouriteRecyclerviewItemBinding.executePendingBindings()
        }
    }

    class OnRecipeItemClickListener(val clickListener: (recipeId: Int) -> Unit) {
        fun onClick(recipeDetail: RecipeDetail) = clickListener(recipeDetail.id!!)
    }

    fun updateList(newList: ArrayList<RecipeDetail>) {
        val oldList = this.favouriteRecipeList
        this.favouriteRecipeList = newList
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
        return favouriteRecipeList.size
    }
}