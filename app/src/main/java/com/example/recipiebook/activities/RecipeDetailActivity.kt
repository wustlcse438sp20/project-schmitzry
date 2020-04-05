package com.example.recipiebook.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipiebook.R
import com.example.recipiebook.data.RecipeDetailResponse
import com.example.recipiebook.data.RecipeSearchItem
import com.example.recipiebook.data.SpoonacularIngredient
import com.example.recipiebook.data.SpoonacularViewModel
import com.example.recipiebook.util.RecipeIngredientListAdapter
import com.example.recipiebook.util.RecipeSearchResultListAdapter
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_recipe_detail.*


class RecipeDetailActivity : AppCompatActivity() {

    lateinit var viewModel: SpoonacularViewModel

    lateinit var recipeItem: RecipeDetailResponse

    var ingredientList: ArrayList<SpoonacularIngredient> = ArrayList()

    var itemId = ""

    var isSaved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)
        itemId = intent!!.extras!!.getString("id").toString()
        isSaved = intent!!.extras!!.getString("saved") != null
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

                Picasso.get().load(recipeItem.image).into(recipeImage)

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

            })

            println("ITEMID")
            println(itemId)

            viewModel.getRecipeDetail(itemId)
        } else {
            // get from FireStore
        }


    }

    fun performAddToBook() {

    }
}
