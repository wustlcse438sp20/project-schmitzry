package com.example.recipiebook.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipiebook.R
import com.example.recipiebook.util.RecipeIngredientListAdapter
import com.example.recipiebook.util.RecipeSearchResultListAdapter
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.recipiebook.data.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_recipe_book_contents.*
import kotlinx.android.synthetic.main.activity_recipe_detail.*
import kotlinx.android.synthetic.main.activity_recipe_detail.loadingBar
import kotlinx.android.synthetic.main.activity_search_result.*


class RecipeDetailActivity : AppCompatActivity() {

    lateinit var viewModel: SpoonacularViewModel

    lateinit var recipeItem: RecipeDetailResponse

    var ingredientList: ArrayList<SpoonacularIngredient> = ArrayList()

    var itemId = ""

    var isSaved = false

    val db = FirebaseFirestore.getInstance()

    var mAuth = FirebaseAuth.getInstance()

    var bookList: ArrayList<RecipeBookRef> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)
        itemId = intent!!.extras!!.getString("id").toString()
        isSaved = intent!!.extras!!.getString("saved") != null

        loadingBar.show()
    }

    override fun onStart() {
        super.onStart()

        var ingredientAdapter = RecipeIngredientListAdapter(ingredientList)
        findViewById<RecyclerView>(R.id.recipeIngredientListContainer).adapter = ingredientAdapter
        findViewById<RecyclerView>(R.id.recipeIngredientListContainer).layoutManager = LinearLayoutManager(this)

        if (!isSaved) {

            viewModel = ViewModelProvider(this).get(SpoonacularViewModel::class.java)

            // get from spoonacular
            viewModel!!.recipeDetailResponse.observe(this, Observer {
                println(it.toString())
                recipeItem = it

                if (recipeItem.image != null) {
                    Picasso.get().load(recipeItem.image).into(recipeImage)
                } else {
                    recipeImage.visibility = View.GONE
                }

                ingredientList.addAll(it.extendedIngredients)

                ingredientAdapter.notifyDataSetChanged()

                recipeTitle.text = recipeItem.title

                cookTime.text = recipeItem.readyInMinutes.toString()

                gotoRecipeOnline.setOnClickListener {
                    val uri = Uri.parse(recipeItem.sourceUrl)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }

                addToRecipeBook.setOnClickListener {
                    performAddToBook()
                }

                loadingBar.hide()

            })

            println(itemId)

            viewModel.getRecipeDetail(itemId)
        } else {
            // get from FireStore
        }


    }

    fun performAddToBook() {
        if (isSaved) {
            // TODO toast cannot add when already saved
        } else {
            // TODO add modal
            db.collection("Users").document(mAuth.currentUser!!.uid).get().addOnSuccessListener { item ->

                val res = item.toObject(UserData::class.java)

                //uData = res!!

                bookList.clear()

                for (b in res!!.books) {
                    bookList.add(b)
                }

                //resultListAdapter.notifyDataSetChanged()
                // TODO pop modal

                // tODO move to add handlers
                val selectedBook = bookList[0]

                db.collection("Books").document(selectedBook.id).get().addOnSuccessListener { item ->

                    println(item.data)

                    val res = item.toObject(FirebaseRecipeBook::class.java)

                    println(res)

                    var bookDetail = res!!
                    /*

                    data class FirebaseRecipeBookRecipe(
                        val id: String = "",
                        val name: String = "",
                        val imageUrl: String = "",
                        val timeToCook: Int = 0,
                        val webUrl: String = "",
                        val ingredients: ArrayList<RecipeIngredient> = ArrayList(),
                        val userNotes: String = ""
                    )

                     */

                    val newRecipe = FirebaseRecipeBookRecipe(id=recipeItem.id.toString(), name=recipeItem.title, imageUrl = recipeItem.image, timeToCook = recipeItem.readyInMinutes, webUrl = recipeItem.sourceUrl)

                    for (item in recipeItem.extendedIngredients) {
                        newRecipe.ingredients.add(RecipeIngredient(name=item.name, amount = item.amount, unit = item.unit))
                    }

                    bookDetail.recipes.add(newRecipe)

                    db.collection("Books").document(selectedBook.id).set(bookDetail)

                }

            }
        }
    }
}
