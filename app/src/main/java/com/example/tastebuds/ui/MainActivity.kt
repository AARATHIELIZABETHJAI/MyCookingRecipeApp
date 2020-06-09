package com.example.tastebuds.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.tastebuds.R
import com.example.tastebuds.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navFragment: Fragment
    private lateinit var appBarConfiguration:AppBarConfiguration
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolBar.toolBar)
        setupNavigation()
    }

    private fun setupNavigation() {
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.accountFragment,
                R.id.favouritesFragment,
                R.id.shoppingListFragment,
                R.id.settingsFragment,
                R.id.helpFragment
            ), mainDrawerLayout
        )
        val navController = findNavController(R.id.nav_host_fragment)

        // Update action bar to reflect navigation
        setupActionBarWithNavController(navController, appBarConfiguration)

//       navController.addOnDestinationChangedListener { controller, destination, arguments ->
//           if (destination.id==R.id.recipeDetailFragment)
//       {
//               binding.toolBar.toolBar.visibility = View.VISIBLE
//       }
//       else            {
//           binding.toolBar.toolBar.visibility = View.VISIBLE
//       }
//       }

        // Tie nav graph to items in nav drawer
        nav_view.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)|| super.onSupportNavigateUp()
    }

}

