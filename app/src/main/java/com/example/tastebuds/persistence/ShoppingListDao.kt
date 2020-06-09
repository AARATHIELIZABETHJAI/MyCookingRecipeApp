package com.example.tastebuds.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tastebuds.ui.adapters.ShoppingList

@Dao
interface ShoppingListDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(ingredient: ShoppingList):Long

    @Delete
    fun delete(ingredient: ShoppingList):Int

    @Query("SELECT * FROM shopping_list_table")
    fun getAllIngredients():LiveData<List<ShoppingList>>
}