package com.example.tastebuds.di.modules

import android.app.Application
import androidx.room.Room
import com.example.tastebuds.persistence.AppDataBase
import com.example.tastebuds.persistence.FavouriteRecipesDao
import com.example.tastebuds.persistence.ShoppingListDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(application: Application): AppDataBase = Room.databaseBuilder(
            application, AppDataBase::class.java, "AppRecipeDataBase")
           .build()

    @Provides
    @Singleton
    internal fun provideFavouriteRecipesDao(appDataBase: AppDataBase): FavouriteRecipesDao =
        appDataBase.getFavouriteRecipesDao()

    @Provides
    @Singleton
    internal fun provideShoppingListDao(appDataBase: AppDataBase): ShoppingListDao =
        appDataBase.getShoppingListDao()
}