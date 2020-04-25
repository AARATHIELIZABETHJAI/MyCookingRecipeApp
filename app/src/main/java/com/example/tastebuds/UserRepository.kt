package com.example.tastebuds

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Query
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Result

class UserRepository(/*appContext: Application,recipe_id: Int*/) {
    //    private var recipesDataBase = RecipesDataBase.getInstance(appContext)
//    private var recipesDao = recipesDataBase.getRecipesDao()
//    private var recipe = recipesDao.getRecipe(recipe_id)
//    private var recipe_list = recipesDao.getAllRecipes()
    var responseData = ArrayList<com.example.tastebuds.Result>()
    var mutableResponse = MutableLiveData<ArrayList<com.example.tastebuds.Result>>()

    fun searchRecipe(
        recipeName: String,
        apiKey: String,
        numberofResults: Int
    ): MutableLiveData<ArrayList<com.example.tastebuds.Result>> {
        MyNetworkApi().searchRecipe(recipeName, apiKey, numberofResults)
            .enqueue(object : Callback<ResponseData> {
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    mutableResponse.value = null
                }

                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>
                ) {
                    if (response.isSuccessful()) {
                        responseData = response.body()?.results as ArrayList<com.example.tastebuds.Result>
                        mutableResponse.setValue(responseData)
                        Log.e("check", "$responseData")
                    }
                }
            })
        return mutableResponse
    }

    fun searchRecipebyId(recipeId : Int,apiKey : String)
    {

    }

    fun insert(recipes: Recipes) {

    }

    fun update(recipes: Recipes) {

    }

    fun delete(recipes: Recipes) {

    }

    fun deleteAllRecipes() {

    }

    // As theses func return livedata, funcs execute in bg thread
//    fun getAllRecipes(): LiveData<List<Recipes>> {
//        return recipe_list
//    }
//
//    fun getRecipe(recipe_id: Int): LiveData<Recipes> {
//        return recipe
//    }
}