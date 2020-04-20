package com.example.recipiebook.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipiebook.R
import com.example.recipiebook.data.FirebaseRecipeBook
import com.example.recipiebook.data.FirebaseRecipeBookRecipe
import com.example.recipiebook.util.RecipeBookListAdapter
import com.example.recipiebook.util.RecipeBookRecipeListAdapter
import com.example.recipiebook.util.RecipeSearchResultListAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_recipe_book_contents.*
import kotlinx.android.synthetic.main.activity_recipe_detail.*
import kotlinx.android.synthetic.main.activity_recipe_detail.loadingBar
import kotlinx.android.synthetic.main.fragment_recipe_book_list.*

class RecipeBookContentsActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    var bookId = ""
    var bookDetail = FirebaseRecipeBook()

    var recipeList: ArrayList<FirebaseRecipeBookRecipe> = ArrayList()

    lateinit var resultListAdapter: RecipeBookRecipeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_book_contents)
    }

    override fun onStart() {
        super.onStart()
        bookId = intent!!.extras!!.getString("bookId")!!

        loadingBar.show()

        resultListAdapter = RecipeBookRecipeListAdapter(::gotoRecipe, recipeList)
        recipeListContainer.adapter = resultListAdapter
        recipeListContainer.layoutManager = LinearLayoutManager(this)

        getRecipes()
    }

    private fun getRecipes() {

        db.collection("Books").document(bookId).get().addOnSuccessListener { item ->

            println(item.data)

            val res = item.toObject(FirebaseRecipeBook::class.java)

            println(res)

            bookDetail = res!!

            recipeList.clear()

            for (b in res!!.recipes) {
                recipeList.add(b)
            }

            println(recipeList)

            resultListAdapter.notifyDataSetChanged()

            bookTitle.text = bookDetail.name

            loadingBar.hide()

            println(resultListAdapter.itemCount)

        }

    }

    fun gotoRecipe(id: String) {
        val i = Intent(this, RecipeDetailActivity::class.java)
        i.putExtra("bookId", bookId)
        i.putExtra("recipeId", id)
        i.putExtra("saved", "1")
        startActivityForResult(i, 0)
    }
}
