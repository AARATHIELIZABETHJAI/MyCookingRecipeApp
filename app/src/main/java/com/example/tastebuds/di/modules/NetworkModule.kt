package com.example.tastebuds.di.modules

import com.example.tastebuds.data.network.MyNetworkApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/recipes/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()


    @Provides
    @Singleton
    internal fun provideApiService(retrofit: Retrofit): MyNetworkApi = retrofit.create(MyNetworkApi::class.java)
}