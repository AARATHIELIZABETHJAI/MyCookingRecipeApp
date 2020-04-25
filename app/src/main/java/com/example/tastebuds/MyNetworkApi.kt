package com.example.tastebuds


import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

// https://api.spoonacular.com/recipes/beb23c70f0264a09b60b9f0aa44b1ea5
interface MyNetworkApi {
    @GET("search?")
    fun searchRecipe(
        @Query("query") recipeName: String,
        @Query("apiKey")apiKey:String,
        @Query("number")numberofResults:Int
    ): Call<ResponseData>

    companion object {
        operator fun invoke(): MyNetworkApi {
            return Retrofit.Builder().baseUrl("https://api.spoonacular.com/recipes/")
                .addConverterFactory(GsonConverterFactory.create()).
                    build().
                    create(MyNetworkApi::class.java)
        }
    }
}