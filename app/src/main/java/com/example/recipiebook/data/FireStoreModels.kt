package com.example.recipiebook.data

data class RecipeIngredient(
    val name: String = "",
    val amount: String = "",
    val unit: String = ""
)

data class RecipeBook(
    val id: String,
    val name: String,
    val count: Int
)

data class FirebaseRecipeBookRecipe(
    val id: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val timeToCook: Int = 0,
    val webUrl: String = "",
    val ingredients: ArrayList<RecipeIngredient> = ArrayList(),
    val userNotes: String = ""
)

data class FirebaseRecipeBook(
    val name: String = "",
    val recipes: ArrayList<FirebaseRecipeBookRecipe> = ArrayList()
)

data class RecipeBookRef(
    val name: String = "",
    val id: String = ""
)

data class UserData(
    val books: ArrayList<RecipeBookRef> = ArrayList()
)

// Collection for UserData is "Users"
// Collection for FirebaseRecipeBook is "Books"