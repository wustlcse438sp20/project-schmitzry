package com.example.recipiebook.data

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiClient {
    const val BASE_URL = "https://api.spoonacular.com/"

    fun makeRetrofitService(): SpoonacularInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(SpoonacularInterface::class.java)
    }
}