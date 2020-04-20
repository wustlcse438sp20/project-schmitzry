package com.example.recipiebook.data

data class SpoonacularIngredient(
    val amount: String,
    val unit: String,
    val name: String
)

data class RecipeDetailResponse(
    val id: Int,
    val title: String,
    val image: String,
    val readyInMinutes: Int,
    val sourceUrl: String,
    val extendedIngredients: List<RecipeIngredient>
)

data class RecipeSearchItem(
    val id: Int,
    val image: String,
    val title: String,
    val readyInMinutes: Int
)

data class RecipeSearchResponse (
    val results: List<RecipeSearchItem>
)