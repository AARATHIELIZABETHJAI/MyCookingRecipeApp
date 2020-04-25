package com.example.tastebuds

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.media.session.MediaButtonReceiver.handleIntent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tastebuds.databinding.ActivityMainBinding
import com.example.tastebuds.databinding.ActivitySearchBinding


class SearchActivity : AppCompatActivity() {
    private lateinit var viewModel: AppViewModel
    private lateinit var binding: ActivitySearchBinding
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Databinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        viewModel = ViewModelProvider(this).get(AppViewModel::class.java)
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                SearchRecentSuggestions(
                    this,
                    MySuggestionContentProvider.AUTHORITY,
                    MySuggestionContentProvider.MODE
                )
                    .saveRecentQuery(query, null)
                searchQuery(query)
            }
        }
    }

    private fun searchQuery(query: String) {
        var recipeList = viewModel.searchRecipe(query)
        recipeList.observe(this, Observer {
            it->{}
            Log.d("output", "ResponseData")
            var recipeList_t:ArrayList<Result> = recipeList 
            recyclerView = binding.recyclerView
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = GridLayoutManager(this, 2)
            recyclerView.adapter = MyAdapter(recipeList_t)
        })
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
}
