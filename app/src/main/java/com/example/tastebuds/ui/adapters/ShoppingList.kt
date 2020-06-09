package com.example.tastebuds.ui.adapters

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list_table")
data class ShoppingList (
    var ingredient : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
}

