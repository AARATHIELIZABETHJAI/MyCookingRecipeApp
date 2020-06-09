package com.example.tastebuds.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tastebuds.R
import com.example.tastebuds.ui.adapters.ShoppingListAdapter
import com.example.tastebuds.databinding.FragmentShoppingListBinding
import com.example.tastebuds.ui.adapters.ShoppingList
import com.example.tastebuds.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_shopping_list.*

/**
 * A simple [Fragment] subclass.
 */
class ShoppingListFragment : Fragment() {
    private lateinit var binding: FragmentShoppingListBinding
    private lateinit var viewModel: AppViewModel
    private var deleteIngredient : ShoppingList? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)
        binding.viewModel = viewModel
        val shoppingListAdapter =
            ShoppingListAdapter(
                requireContext(),
                R.layout.shopping_listview_item,
                { ingredient -> deletefromList(ingredient) })
        shoppingListView.adapter = shoppingListAdapter
        deleteButton.setOnClickListener{
            deleteButton.visibility = View.GONE
            viewModel.deleteFromShoppingList(deleteIngredient!!)
        }
        viewModel.shoppingListItems.observe(viewLifecycleOwner, Observer {
            shoppingListAdapter.updateList(it)
        })
    }

    private fun deletefromList(shoppingList: ShoppingList)
    {
        deleteIngredient = shoppingList
        deleteButton.visibility = View.VISIBLE
    }
}
