package com.example.tastebuds

import com.example.tastebuds.ApiHelper.myNetworkApi
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

    suspend fun searchRecipeById(id: Int, apiKey: String):ServiceResponse<RecipeDetail> {
        return safeApiCall { myNetworkApi.searchRecipeByIdService(id, apiKey) }
    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): ServiceResponse<T> {
        return try {
            val myResp = call.invoke()
            if (myResp.isSuccessful) {
                ServiceResponse.Success(myResp.body()!!)
            } else {
                ServiceResponse.Error(myResp.errorBody()?.string() ?: "Something goes wrong")
            }

        } catch (e: Exception) {
            ServiceResponse.Error(e.message ?: "Internet error runs")
        }
    }
}
