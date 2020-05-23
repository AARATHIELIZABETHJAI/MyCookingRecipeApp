package com.example.tastebuds

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tastebuds.databinding.RecipeListFragmentBinding


class RecipeListFragment : Fragment() {
    private lateinit var binding: RecipeListFragmentBinding
    private lateinit var viewModel: AppViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private lateinit var ctx: Context

    companion object {
        fun newInstance() = RecipeListFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("FRAG", "onCreateView")
        binding = RecipeListFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("FRAG", "onActivityCreated")
        viewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)
        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(ctx, 2)
        myAdapter = MyAdapter(MyAdapter.OnRecipeItemClickListener { recipeId ->
            viewModel.onRecipeItemtClicked(recipeId)
        })
        recyclerView.adapter = myAdapter
        viewModel.recipeList.observe(viewLifecycleOwner, Observer {
            myAdapter.updateList(it)
        })

        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            Log.e("FRAG", "CHECKKKKKKKKKKKKKKKKKKKKKKKKK")
            if(viewModel.navigateToDetail.value!=null) {
                if (findNavController().currentDestination?.id == R.id.recipeListFragment) {
                    findNavController().navigate(R.id.action_recipeListFragment_to_recipeDetailFragment)
                }
                viewModel.onRecipeDetailNavigated()
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("FRAG", "onDestroyView")
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDetach() {
        super.onDetach()
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.e("FRAG", "onDestroy")
    }
}
