package com.example.tastebuds.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tastebuds.R
import com.example.tastebuds.databinding.FragmentFavouritesBinding
import com.example.tastebuds.ui.adapters.FavouriteRecipesAdapter
import com.example.tastebuds.viewmodel.AppViewModel
import com.example.tastebuds.viewmodel.AppViewModelFactory
import kotlinx.android.synthetic.main.fragment_favourites.*

/**
 * A simple [Fragment] subclass.
 */
class FavouritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var viewModel: AppViewModel
    private lateinit var favouriteRecipesAdapter: FavouriteRecipesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(),
            AppViewModelFactory(requireActivity().application)
        ).get(
            AppViewModel::class.java)

        initRecyclerView()
        subscribeObservers()
    }

    private fun initRecyclerView() {
        recycler_view_fav.setHasFixedSize(true)
        recycler_view_fav.layoutManager = GridLayoutManager(context, 2)
        favouriteRecipesAdapter = FavouriteRecipesAdapter(FavouriteRecipesAdapter.OnRecipeItemClickListener { recipeId ->
            viewModel.onFavouriteItemClicked(recipeId)
        },{recipeDetail->viewModel.insertFavouriteRecipe(recipeDetail = recipeDetail)},{recipeDetail->viewModel.deleteFavouriteRecipe(recipeDetail = recipeDetail)})
        recycler_view_fav.adapter = favouriteRecipesAdapter
    }

    private fun subscribeObservers() {

        viewModel.allfavouriteRecipes.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                favouriteRecipesAdapter.updateList(it)
            }
        })

        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            if (viewModel.navigateToDetail.value != null) {
                if(viewModel.navigateToDetail.value!!)
                {
                    if (findNavController().currentDestination?.id == R.id.favouritesFragment) {
                        findNavController().navigate(R.id.action_favouritesFragment_to_recipeDetailFragment)
                        val toolbar = activity?.findViewById<Toolbar>(R.id.toolBar)
                        toolbar?.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent));
                    }
                    viewModel.onRecipeDetailNavigated()
                }
            }
        })
    }
}
