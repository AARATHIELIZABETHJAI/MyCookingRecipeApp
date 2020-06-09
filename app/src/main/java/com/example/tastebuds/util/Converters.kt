package com.example.tastebuds.util

import androidx.room.TypeConverter
import com.example.tastebuds.persistence.models.ExtendedIngredient
import com.example.tastebuds.persistence.models.Nutrition
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converters {
    @TypeConverter // note this annotation
    fun fromAnalyzedInstructionsList(analyzedInstructions: List<Any?>?): String? {
        if (analyzedInstructions == null) {
            return null
        }
        val gson = Gson()
        val type = object :
            TypeToken<List<Any?>?>() {}.type
        return gson.toJson(analyzedInstructions, type)
    }

    @TypeConverter // note this annotation
    fun toAnalyzedInstructionsList(analyzedInstructions: String?): List<Any>? {
        if (analyzedInstructions == null) {
            return null
        }
        val gson = Gson()
        val type = object :
            TypeToken<List<Any?>?>() {}.type
        return gson.fromJson<List<Any>>(analyzedInstructions, type)
    }

    @TypeConverter // note this annotation
    fun fromNutrition(nutrition: Nutrition?): String? {
        if (nutrition == null) {
            return null
        }
        val gson = Gson()
        val type = object :
            TypeToken<Nutrition?>() {}.type
        return gson.toJson(nutrition, type)
    }

    @TypeConverter // note this annotation
    fun toNutrition(nutrition: String?): Nutrition? {
        if (nutrition == null) {
            return null
        }
        val gson = Gson()
        val type = object :
            TypeToken<Nutrition>() {}.type
        return gson.fromJson<Nutrition>(nutrition, type)
    }

    @TypeConverter // note this annotation
    fun fromextendedIngredients(extendedIngredients: List<ExtendedIngredient?>?): String? {
        if (extendedIngredients == null) {
            return null
        }
        val gson = Gson()
        val type = object :
            TypeToken<List<ExtendedIngredient?>?>() {}.type
        return gson.toJson(extendedIngredients, type)
    }

    @TypeConverter // note this annotation
    fun toextendedIngredients(extendedIngredients: String?): List<ExtendedIngredient>? {
        if (extendedIngredients == null) {
            return null
        }
        val gson = Gson()
        val type = object :
            TypeToken<List<ExtendedIngredient?>?>() {}.type
        return gson.fromJson<List<ExtendedIngredient>>(extendedIngredients, type)
    }

}