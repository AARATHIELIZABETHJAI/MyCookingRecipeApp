package com.example.tastebuds.data.network.responses

import com.example.tastebuds.persistence.models.RecipeDetail


data class DailyRecipe(
    val recipes: List<RecipeDetail>
)

