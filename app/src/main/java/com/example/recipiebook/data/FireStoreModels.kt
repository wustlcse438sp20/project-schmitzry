package com.example.recipiebook.data

data class RecipeIngredient(
    val name: String,
    val amount: String,
    val unit: String
)

data class RecipeBook(
    val id: String,
    val name: String,
    val count: Int
)

data class RecipeBookRecipe(
    val id: String,
    val name: String,
    val imageUrl: String,
    val timeToCook: Int,
    val webUrl: String,
    val ingredients: String, // NOTE: This will be a CSV - name1,amount1,units1;name2,amount2,units2; for ease of use
    val userNotes: String
)