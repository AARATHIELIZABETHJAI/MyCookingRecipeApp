package com.example.tastebuds.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tastebuds.RecipeDetail

@Dao
interface FavouriteRecipesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(recipeDetail: RecipeDetail)

    @Update
    fun update(recipeDetail: RecipeDetail)

    @Delete
    fun delete(recipeDetail: RecipeDetail): Int

    @Query("DELETE FROM favourite_recipes_table")
    fun deleteAllFavouriteRecipes()

    @Query("SELECT * from favourite_recipes_table WHERE id = :key")
    fun getFavouriteRecipe(key: Int): RecipeDetail?

    @Query("SELECT * FROM favourite_recipes_table")
    fun getAllFavouriteRecipes(): LiveData<List<RecipeDetail>>
}