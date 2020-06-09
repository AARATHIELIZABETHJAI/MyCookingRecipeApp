package com.example.tastebuds.ui.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tastebuds.MySuggestionContentProvider
import com.example.tastebuds.R
import com.example.tastebuds.databinding.FragmentHomeBinding
import com.example.tastebuds.ui.adapters.CategoriesAdapter
import com.example.tastebuds.ui.adapters.CuisinesAdapter
import com.example.tastebuds.viewmodel.AppViewModel
import com.example.tastebuds.viewmodel.AppViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var ctx: Context
    private lateinit var viewModel: AppViewModel
    private lateinit var binding:FragmentHomeBinding

    override fun onAttach(context: Context) {
        ctx = context
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentHomeBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(),
            AppViewModelFactory(requireActivity().application)
        ).get(
            AppViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.searchDailyRecipe()
        initSearchView()
        subscribeObservers()
        initRecyclerView()
    }

    private fun subscribeObservers()
    {
        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            if (it) {
                if (findNavController().currentDestination?.id == R.id.homeFragment) {
                    findNavController().navigate(R.id.action_homeFragment_to_recipeDetailFragment)
                    viewModel.onRecipeDetailNavigated()
                }
            }
        })

        viewModel.navigateToList.observe(viewLifecycleOwner, Observer {
            if (it) {
                if (findNavController().currentDestination?.id == R.id.homeFragment) {
                    findNavController().navigate(R.id.action_homeFragment_to_recipeListFragment)
                    viewModel.onRecipeListNavigated()
                }
            }
        })
    }

    private fun initRecyclerView() {
        val recyclerView = binding.categoriesRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(ctx, 3)
        val categoriesAdapter =
            CategoriesAdapter(
                ctx,
                CategoriesAdapter.OnCategoryItemClickListener { categoryName ->
                    viewModel.onCategoryItemtClicked(categoryName)
                })
        recyclerView.adapter = categoriesAdapter

        val recyclerViewCusines = binding.cuisinesRecyclerView
        recyclerViewCusines.setHasFixedSize(true)
        recyclerViewCusines.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
        val cuisinesAdapter = CuisinesAdapter(
            ctx,
            CuisinesAdapter.OnCuisineItemClickListener { cuisineName ->
                viewModel.onCuisineItemtClicked(cuisineName)
            })
        recyclerViewCusines.adapter = cuisinesAdapter
    }

    private fun initSearchView()
    {
        var searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        recipeSearchview.setSearchableInfo(searchManager.getSearchableInfo(getActivity()?.getComponentName()))

        recipeSearchview.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return true
            }
            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = recipeSearchview.suggestionsAdapter.cursor
                cursor.moveToPosition(position)
                val suggestionQuery =
                    cursor.getString(2) //2 is the index of col containing suggestion name.
                viewModel.searchRecipe(suggestionQuery!!)
                recipeSearchview.setQuery(suggestionQuery, true) //setting suggestion
                return true
            }
        })

        recipeSearchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val suggestions = SearchRecentSuggestions(
                    context,
                    MySuggestionContentProvider.AUTHORITY,
                    MySuggestionContentProvider.MODE
                )
                suggestions.saveRecentQuery(query, null)
                recipeSearchview.clearFocus()
                viewModel.searchRecipe(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}
