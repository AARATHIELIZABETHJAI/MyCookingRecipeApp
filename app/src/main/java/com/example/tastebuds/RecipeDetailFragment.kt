package com.example.tastebuds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tastebuds.databinding.FragmentRecipeDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_recipe_detail.*

/**
 * A simple [Fragment] subclass.
 */
class RecipeDetailFragment : Fragment() {
    private lateinit var binding: FragmentRecipeDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentRecipeDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val detailPagerAdapter = DetailPagerAdapter(this)
        detailViewPager.adapter = detailPagerAdapter
        TabLayoutMediator(mtab_layout,detailViewPager,TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            when(position)
            {
                0->{tab.text = "OverView"
                tab.setIcon(R.drawable.ic_account_circle_black_24dp)}
                1->{tab.text = "Ingredients"
                    tab.setIcon(R.drawable.ic_account_circle_black_24dp)}
                2->{tab.text = "Steps"
                    tab.setIcon(R.drawable.ic_account_circle_black_24dp)}

            }
        }).attach()
    }

}
