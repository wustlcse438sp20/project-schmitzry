package com.example.recipiebook.data

import retrofit2.Response
import retrofit2.http.*

interface SpoonacularInterface {

    @GET("recipes/{id}/information")
    suspend fun recipeDetailSearch(@Path("id") id: String, @Query("apiKey") key: String): Response<RecipeDetailResponse>

    @GET("recipes/search")
    suspend fun recipeSearchForTerm(@Query("apiKey") key: String, @Query("query") query: String, @Query("diet") diet: String, @Query("cuisine") cuisine: String): Response<RecipeSearchResponse>
}