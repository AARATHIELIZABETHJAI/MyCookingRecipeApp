package com.example.tastebuds.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tastebuds.R
import com.example.tastebuds.databinding.FragmentRecipeDetailBinding
import com.example.tastebuds.ui.adapters.DetailPagerAdapter
import com.example.tastebuds.viewmodel.AppViewModel
import com.example.tastebuds.viewmodel.AppViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_recipe_detail.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass.
 */
class RecipeDetailFragment : Fragment(),KodeinAware {
    private lateinit var binding: FragmentRecipeDetailBinding
    private lateinit var viewModel: AppViewModel
    var fabVisible = false
    override val kodein: Kodein by kodein()
    private val appViewModelFactory: AppViewModelFactory by instance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(),appViewModelFactory
        ).get(
            AppViewModel::class.java)
        binding.viewModel = viewModel
        subscribeObservers()
        fab.setOnClickListener { view ->
            if (!fabVisible) {
                fabVisible = true
                fab.setColorFilter(ContextCompat.getColor(requireContext(),
                    R.color.grey
                ));
                Snackbar.make(view, "Recipe Added to Favourites", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
                viewModel.insertFavouriteRecipe(viewModel.recipeDetail.value!!)
            } else {
                fabVisible = false
                fab.setColorFilter(ContextCompat.getColor(requireContext(),
                    R.color.white
                ));
                Snackbar.make(view, "Recipe removed from Favourites", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
                viewModel.deleteFavouriteRecipe(viewModel.recipeDetail.value!!)
            }
        }
    }

    private fun subscribeObservers() {
        viewModel.recipeDetail.observe(viewLifecycleOwner, Observer {
         if(!(viewModel.loading.value!!)) {
             if (it != null)
                nestedView.visibility = View.VISIBLE
                appBarLayout.visibility = View.VISIBLE
                setUpViewPager()
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                nestedView.visibility = View.GONE
                appBarLayout.visibility = View.GONE
//                progressBarDetail.visibility = View.VISIBLE
            }
        })

        viewModel.errorloading.observe(viewLifecycleOwner, Observer {
            if (it) {
//                progressBarDetail.visibility = View.GONE
                val snackBar = Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    viewModel.errorMessage.value.toString(), Snackbar.LENGTH_LONG
                )
                snackBar.show()
            }
        })
    }

    private fun setUpViewPager() {
        val detailPagerAdapter =
            DetailPagerAdapter(this)
        detailViewPager.adapter = detailPagerAdapter
        TabLayoutMediator(
            mtab_layout,
            detailViewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "OverView"
                        tab.setIcon(R.drawable.ic_content_paste_black_24dp)
                    }
                    1 -> {
                        tab.text = "Ingredients"
                        tab.setIcon(R.drawable.ic_shopping_basket_black_24dp)
                    }
                    2 -> {
                        tab.text = "Steps"
                        tab.setIcon(R.drawable.ic_view_list_black_24dp)
                    }

                }
            }).attach()
    }
}
