package com.example.tastebuds.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tastebuds.util.Converters
import com.example.tastebuds.persistence.models.RecipeDetail
import com.example.tastebuds.ui.adapters.ShoppingList

@Database(
    entities = [RecipeDetail::class,ShoppingList::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getFavouriteRecipesDao(): FavouriteRecipesDao
    abstract fun getShoppingListDao(): ShoppingListDao

    companion object {
        @Volatile
        private var instance: AppDataBase? = null
        private val LOCK = Any()
        fun getInstance(context: Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: buildDatabase(
                    context
                ).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            "AppRecipeDataBase"
        ).build()

    }
}