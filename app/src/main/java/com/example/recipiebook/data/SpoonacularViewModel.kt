package com.example.recipiebook.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SpoonacularViewModel (application: Application): AndroidViewModel(application) {

    var recipeDetailResponse: MutableLiveData<RecipeDetailResponse> = MutableLiveData()

    var recipeSearchResponse: MutableLiveData<RecipeSearchResponse> = MutableLiveData()

    var spoonacularRepository: SpoonacularRepository = SpoonacularRepository()


    fun getRecipeDetail(id: String) {
        println("getting with id ${id}")
        spoonacularRepository.recipeDetailSearch(recipeDetailResponse, id)
    }

    fun getRecipiesForQuery(query: String, diet: String = "", cuisine: String = "") {
        spoonacularRepository.recipeSearchForTerm(recipeSearchResponse, query, diet, cuisine)
    }
}