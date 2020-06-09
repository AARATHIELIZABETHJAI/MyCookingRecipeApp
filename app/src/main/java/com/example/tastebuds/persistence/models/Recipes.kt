package com.example.tastebuds.persistence.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes_table")
data class Recipes (
        private var recipe_id:Int? = null,
        private var recipe_title :String?  = null,
        private var image:String? = null,
        private var image_url:String? = null)
{
    @PrimaryKey(autoGenerate = true)
    var id:Int = 1
}