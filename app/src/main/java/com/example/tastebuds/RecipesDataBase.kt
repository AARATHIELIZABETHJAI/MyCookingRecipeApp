package com.example.tastebuds

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Recipes::class],
    version = 1
)
abstract class RecipesDataBase : RoomDatabase() {

    abstract fun getRecipesDao(): RecipesDao

    companion object {
        private var instance: RecipesDataBase? = null
        private val LOCK = Any()
        fun getInstance(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RecipesDataBase::class.java,
            "AppRecipeDataBase"
        ).build()

    }
}