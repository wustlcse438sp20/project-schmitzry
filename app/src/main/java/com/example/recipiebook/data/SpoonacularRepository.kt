package com.example.recipiebook.data

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

const val API_KEY = "559ed69850f448ae9fbc198c573cb79f"

class SpoonacularRepository {
    private val service = ApiClient.makeRetrofitService()

    fun recipeDetailSearch(resBody: MutableLiveData<RecipeDetailResponse>, id: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val response = service.recipeDetailSearch(id, API_KEY)

            println(response.raw().toString())

            withContext(Dispatchers.Main) {
                try{
                    if(response.isSuccessful) {
                        resBody.value = response.body()
                    }
                } catch (e: HttpException) {
                    println("Http error")
                }
            }
        }
    }

    fun recipeSearchForTerm(resBody: MutableLiveData<RecipeSearchResponse>, query: String, diet: String, cuisine: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val response = service.recipeSearchForTerm(API_KEY, query, diet, cuisine)

            println(response.raw().toString())

            withContext(Dispatchers.Main) {
                try{
                    if(response.isSuccessful) {
                        resBody.value = response.body()
                    }
                } catch (e: HttpException) {
                    println("Http error")
                }
            }
        }
    }

}