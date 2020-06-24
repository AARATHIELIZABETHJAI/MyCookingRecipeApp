package com.example.tastebuds

import android.app.Application
import com.example.tastebuds.data.network.ApiHelper
import com.example.tastebuds.data.network.MyNetworkApi
import com.example.tastebuds.data.repository.UserRepository
import com.example.tastebuds.persistence.AppDataBase
import com.example.tastebuds.viewmodel.AppViewModelFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BaseApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@BaseApplication))

        bind<MyNetworkApi>() with singleton {
            Retrofit.Builder().
            baseUrl("https://api.spoonacular.com/recipes/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().create(MyNetworkApi::class.java)
        }
        bind() from singleton { ApiHelper(instance()) }
        bind() from singleton { AppDataBase(instance()) }
        bind() from singleton { UserRepository(instance(),instance()) }
        bind() from provider { AppViewModelFactory(instance()) }
    }
}