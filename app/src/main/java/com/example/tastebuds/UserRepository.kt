package com.example.tastebuds


import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Query
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import kotlin.Result

class UserRepository(/*appContext: Application,recipe_id: Int*/) {
    //    private var recipesDataBase = RecipesDataBase.getInstance(appContext)
//    private var recipesDao = recipesDataBase.getRecipesDao()
//    private var recipe = recipesDao.getRecipe(recipe_id)
//    private var recipe_list = recipesDao.getAllRecipes()
    var responseData = ArrayList<com.example.tastebuds.Result>()
    var mutableResponse = MutableLiveData<ArrayList<com.example.tastebuds.Result>>()
    lateinit var recipeDetail:RecipeDetail
//    var recipeDetail = MutableLiveData<RecipeDetail>()

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
                        responseData =
                            response.body()?.results as ArrayList<com.example.tastebuds.Result>
                        mutableResponse.setValue(responseData)
                        Log.e("check", "$responseData")
                    }
                }
            })
        return mutableResponse
    }

    suspend fun searchRecipebyId(recipeId: Int, apiKey: String)=ApiHelper.searchRecipeById(recipeId,apiKey)

//    suspend fun searchRecipebyId(recipeId: Int, apiKey: String): RecipeDetail {
//        try {
//            var response = MyNetworkApi().searchRecipeById(recipeId, apiKey)
//            if (response.isSuccessful()) {
//                println("onSuccess")
//                recipeDetail = response.body() as RecipeDetail
//                println("hi")
//            }
//            else
//            {
//                println("hello")
//            }
//        } catch (e: Exception) {
////                _response.value = "Failure: ${e.message}"
//        }
//        println("hELLO")
//        return recipeDetail
//    }

//    fun searchRecipebyId(recipeId : Int,apiKey : String):MutableLiveData<RecipeDetail>
//    {
//        println("SEARCHING...............")
//        MyNetworkApi().searchRecipeById(recipeId, apiKey)
//            .enqueue(object : Callback<RecipeDetail> {
//
//                override fun onFailure(call: Call<RecipeDetail>, t: Throwable) {
//                    println("onFailure")
//                    mutableResponse.value = null
//                }
//
//                override fun onResponse(
//                    call: Call<RecipeDetail>,
//                    response: Response<RecipeDetail>
//                ) {
//                    if (response.isSuccessful()) {
//                        println("onSuccess")
//                        println("RESPONSEBODY ${response.body()}")
//                        recipeDetail.value = response.body()!!
//                    }
//                    else
//                    { println("Failure")}
//                }
//            })
//        println("RESPONSE ${recipeDetail.value}")
//        return recipeDetail
//    }

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