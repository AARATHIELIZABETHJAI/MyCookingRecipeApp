package com.example.tastebuds

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

//https://api.spoonacular.com/recipes/search?query=yogurt&apiKey=beb23c70f0264a09b60b9f0aa44b1ea5
const val NUMBER_OF_RESULTS = 10;
const val APIKEY:String = "beb23c70f0264a09b60b9f0aa44b1ea5"

class AppViewModel:ViewModel() {/*brackets of Viewmodel means calling constructor of view model which is necessary*/

    fun searchRecipe(recipeName:String):LiveData<ArrayList<Result>> {
        var liveResponse = UserRepository().searchRecipe(recipeName,APIKEY,NUMBER_OF_RESULTS)
        return liveResponse
    }

    fun searchRecipebyId(recipeId:Int)
    {
        UserRepository().searchRecipebyId(recipeId,APIKEY)
    }
}