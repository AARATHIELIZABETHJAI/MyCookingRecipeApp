package com.example.tastebuds

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val NUM_PAGES = 3

class DetailPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    private val mFragmentList: MutableList<Fragment> = ArrayList()
    private val mFragmentTitleList: List<String> = ArrayList()

    override fun getItemCount() = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        var fragment:Fragment? = null
        when(position)
        {
            0-> fragment = OverviewFragment()
            1-> fragment = IngredientsFragment()
            2-> fragment = CookingStepsFragment()
            else -> AssertionError()
        }
        return fragment!!
    }


}