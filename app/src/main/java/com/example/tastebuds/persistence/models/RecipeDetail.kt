package com.example.tastebuds.persistence.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_recipes_table")
data class RecipeDetail(
    @PrimaryKey
    val id: Int,
    val title: String,
    val image: String,
    val imageType: String,
    val analyzedInstructions: List<Any>,
    val extendedIngredients: List<ExtendedIngredient>,
    val occasions: List<Any>,
    val pricePerServing: Double,
    val readyInMinutes: Int,
    val servings: Int,
    val dairyFree: Boolean,
    val glutenFree: Boolean,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val nutrition: Nutrition
)

data class ExtendedIngredient(
    val original: String,
    val name: String
)


data class Nutrition(
    val caloricBreakdown: CaloricBreakdown
)



   
data class Nutrient(
    val amount: Int,
    val name: String,
    val unit: String
)

data class CaloricBreakdown(
    val percentCarbs: Double,
    val percentFat: Double,
    val percentProtein: Double
)


