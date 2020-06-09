package com.example.tastebuds.ui.adapters

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.tastebuds.ui.adapters.ShoppingList
import kotlinx.android.synthetic.main.shopping_listview_item.view.*
import java.util.*


class ShoppingListAdapter(
    context: Context,
    val itemLayoutResource: Int,
    val onItemClick: (ShoppingList) -> Unit
) :
    ArrayAdapter<String>(context, itemLayoutResource) {
    private var ingredientList = ArrayList<ShoppingList>()

//    class OnItemClickListener{
//        fun onClick(ingredient:String) = itemC
//    }

    fun updateList(newList: ArrayList<ShoppingList>) {
        ingredientList = newList
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(itemLayoutResource, parent, false)
        itemView.checkboxItem.text = ingredientList[position].ingredient
        itemView.checkboxItem.isChecked = true
        itemView.checkboxItem.setOnCheckedChangeListener {buttonView, isChecked ->
            print("hi")
            onItemClick(ingredientList[position])
        }
        return itemView
    }

    override fun getCount(): Int {
        return ingredientList.size
    }
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val inflater = context
//            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val rowView: View = inflater.inflate(itemLa), parent, false)
//        val textView = rowView.findViewById<View>(R.id.label) as TextView
//    }
}