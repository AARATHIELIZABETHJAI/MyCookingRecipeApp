package com.example.tastebuds

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecipesDao {
    @Insert
    fun insert(recipes: Recipes)

    @Update
    fun update(recipes: Recipes)

    @Update
    fun delete(recipes: Recipes)

    @Query("DELETE FROM recipes_table")
    fun deleteAllRecipes()

    @Query("SELECT * FROM recipes_table")
    fun getAllRecipes():LiveData<List<Recipes>>

    @Query("SELECT * FROM recipes_table WHERE recipe_id = recipe_id")
    fun getRecipe(recipe_id : Int): LiveData<Recipes>

}