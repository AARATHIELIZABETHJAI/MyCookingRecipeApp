package com.example.tastebuds.data.repository


import androidx.lifecycle.MutableLiveData
import com.example.tastebuds.persistence.FavouriteRecipesDao
import com.example.tastebuds.persistence.models.RecipeDetail
import com.example.tastebuds.data.network.responses.Result
import com.example.tastebuds.persistence.ShoppingListDao
import com.example.tastebuds.data.network.ApiHelper
import com.example.tastebuds.persistence.AppDataBase
import com.example.tastebuds.ui.adapters.ShoppingList

class UserRepository(private val apiHelper: ApiHelper,private val appDataBase: AppDataBase) {
    lateinit var recipeDetail: RecipeDetail
    private val favouriteRecipesDao = appDataBase.getFavouriteRecipesDao()
    private val shoppingListDao = appDataBase.getShoppingListDao()
    var allFavouriteRecipes = favouriteRecipesDao.getAllFavouriteRecipes()
    var shoppingListItems = shoppingListDao.getAllIngredients()

    suspend fun searchRecipe(recipeName: String, apiKey: String, numberofResults: Int) =
        apiHelper.searchRecipe(recipeName, apiKey, numberofResults)

    suspend fun searchRecipebyId(recipeId: Int, includeNutrition: Boolean, apiKey: String) =
        apiHelper.searchRecipeById(recipeId, includeNutrition, apiKey)

    suspend fun searchDailyRecipe(apiKey: String, numberofResults: Int) =
        apiHelper.searchDailyRecipe(apiKey, numberofResults)

    fun insertFavouriteRecipe(recipeDetail: RecipeDetail) {
        favouriteRecipesDao.insert(recipeDetail)
    }

    fun deleteFavouriteRecipe(recipeDetail: RecipeDetail) {
        favouriteRecipesDao.delete(recipeDetail)
    }

    fun deleteAllFavouriteRecipes() {
        favouriteRecipesDao.deleteAllFavouriteRecipes()
    }

    fun getFavouriteRecipe(recipeId: Int) = favouriteRecipesDao.getFavouriteRecipe(recipeId)

    fun insertToShoppingList(shoppingList: ShoppingList) = shoppingListDao.insert(shoppingList)

    fun deleteFromShoppingList(shoppingList: ShoppingList) = shoppingListDao.delete(shoppingList)

}