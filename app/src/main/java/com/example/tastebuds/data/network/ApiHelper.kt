package com.example.tastebuds.data.network

import com.example.tastebuds.data.network.responses.DailyRecipe
import com.example.tastebuds.data.network.responses.ResponseData
import com.example.tastebuds.data.network.responses.ServiceResponse
import com.example.tastebuds.persistence.models.RecipeDetail
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiHelper {

    lateinit var myNetworkApi: MyNetworkApi

    init {
        createService()
    }

    private fun createService() {
        myNetworkApi = Retrofit.Builder().baseUrl("https://api.spoonacular.com/recipes/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(MyNetworkApi::class.java)
    }

    suspend fun searchRecipeById(id: Int,includeNutrition:Boolean, apiKey: String): ServiceResponse<RecipeDetail> {
        return safeApiCall {
            myNetworkApi.searchRecipeByIdService(
                id,
                includeNutrition,
                apiKey
            )
        }
    }

    suspend fun searchRecipe(recipeName: String, apiKey: String, numberofResults: Int): ServiceResponse<ResponseData> {
        return safeApiCall {
            myNetworkApi.searchRecipe(
                recipeName,
                apiKey,
                numberofResults
            )
        }
    }

    suspend fun searchDailyRecipe(apiKey: String,numberofResults: Int): ServiceResponse<DailyRecipe>
    {
        return safeApiCall {
            myNetworkApi.searchDailyRecipe(
                numberofResults,
                apiKey
            )
        }
    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): ServiceResponse<T> {
        return try {
            val myResp = call.invoke()
            if (myResp.isSuccessful) {
                ServiceResponse.Success(myResp.body()!!)
            } else {
                ServiceResponse.Error(
                    myResp.errorBody()?.string() ?: "Something goes wrong"
                )
            }

        } catch (e: Exception) {
            ServiceResponse.Error(
                e.message ?: "Internet error runs"
            )
        }
    }
}
