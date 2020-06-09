package com.example.tastebuds.data.network


import com.example.tastebuds.data.network.responses.DailyRecipe
import com.example.tastebuds.persistence.models.RecipeDetail
import com.example.tastebuds.data.network.responses.ResponseData
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// https://api.spoonacular.com/recipes/beb23c70f0264a09b60b9f0aa44b1ea5
interface MyNetworkApi {
    @GET("search?")
    suspend fun searchRecipe(
        @Query("query") recipeName: String,
        @Query("apiKey")apiKey:String,
        @Query("number")numberofResults:Int
    ): Response<ResponseData>

    @GET("{id}/information?")
    suspend fun searchRecipeByIdService(
        @Path("id") recipeName: Int,
        @Query("includeNutrition")includeNutrition:Boolean,
        @Query("apiKey")apiKey:String
    ): Response<RecipeDetail>

    @GET("random?")
    suspend fun searchDailyRecipe(
        @Query("number")numberofResults:Int,
        @Query("apiKey")apiKey:String
    ):Response<DailyRecipe>

    companion object {
        operator fun invoke(): MyNetworkApi {
            return Retrofit.Builder().baseUrl("https://api.spoonacular.com/recipes/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(MyNetworkApi::class.java)
        }
    }





}