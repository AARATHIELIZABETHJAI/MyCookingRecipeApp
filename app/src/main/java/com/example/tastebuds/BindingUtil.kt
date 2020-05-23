package com.example.tastebuds

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.net.URL


    @BindingAdapter("app:image")
    fun setImage(view: ImageView, url: String) {
        val base = URL("https://spoonacular.com/recipeImages/")
        val mergedURL = URL(base, url)
        Glide.with(view).load(mergedURL).apply( RequestOptions().centerCrop()).into(view);
    }
