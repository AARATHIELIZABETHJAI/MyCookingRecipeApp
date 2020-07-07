package com.example.tastebuds.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tastebuds.ui.adapters.RecipeListAdapter
import com.example.tastebuds.R
import com.example.tastebuds.databinding.RecipeListFragmentBinding
import com.example.tastebuds.viewmodel.AppViewModel
import com.example.tastebuds.viewmodel.AppViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.recipe_list_fragment.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class RecipeListFragment : Fragment(),KodeinAware {
    private lateinit var binding: RecipeListFragmentBinding
    private lateinit var viewModel: AppViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: RecipeListAdapter
    private lateinit var ctx: Context
    override val kodein: Kodein by kodein()
    private val appViewModelFactory: AppViewModelFactory by instance()


    companion object {
        fun newInstance() =
            RecipeListFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipeListFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(),appViewModelFactory
        ).get(
            AppViewModel::class.java)

        initRecyclerView()
        subscribeObservers()
    }

    private fun initRecyclerView() {
        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(ctx, 2)
        myAdapter =
            RecipeListAdapter(
                RecipeListAdapter.OnRecipeItemClickListener { recipeId ->
                    viewModel.onRecipeItemtClicked(recipeId)
                })

        recyclerView.adapter = myAdapter
    }

    private fun subscribeObservers() {

        viewModel.recipeList.observe(viewLifecycleOwner, Observer {
            if(it!=null) {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                myAdapter.updateList(it)
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it)
            {
                progressBar.visibility=View.VISIBLE
            }
        })

        viewModel.errorloading.observe(viewLifecycleOwner, Observer {
            if (it)
            {
                progressBar.visibility = View.GONE
                val snackBar = Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    viewModel.errorMessage.value.toString(), Snackbar.LENGTH_LONG
                )
                snackBar.show()
            }
        })

        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            if (viewModel.navigateToDetail.value != null) {
                if(viewModel.navigateToDetail.value!!)
                {
                    if (findNavController().currentDestination?.id == R.id.recipeListFragment) {
                        findNavController().navigate(R.id.action_recipeListFragment_to_recipeDetailFragment)
                       val toolbar = activity?.findViewById<Toolbar>(R.id.toolBar)
                           toolbar?.setBackgroundColor(ContextCompat.getColor(ctx, android.R.color.transparent));
                    }
                    viewModel.onRecipeDetailNavigated()
                }
            }
        })
    }
}
