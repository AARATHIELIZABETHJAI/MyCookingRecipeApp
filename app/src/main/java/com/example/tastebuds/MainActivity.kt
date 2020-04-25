package com.example.tastebuds

import android.app.SearchManager
import android.app.SearchableInfo
import android.content.ComponentName
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.getSystemService
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tastebuds.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.app_bar.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var navFragment: Fragment
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Databinding
//        val binding:ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
//        val viewModel = ViewModelProvider(this).get(AppViewModel::class.java)
//        binding.viewmodel = viewModel
//        // View Binding
        val binding:ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolBar.toolBar)
        drawerLayout = binding.mainDrawerLayout
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding.mainDrawerLayout,
            binding.toolBar.toolBar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.search_menu, menu)
        var searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        var searchMenuItem = menu?.findItem(R.id.search_view) as MenuItem
        var searchView : SearchView = searchMenuItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(ComponentName(this,SearchActivity::class.java)))
        return true
    }
}

