package com.example.recipiebook.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipiebook.R
import com.example.recipiebook.data.RecipeSearchItem
import com.example.recipiebook.data.SpoonacularViewModel
import com.example.recipiebook.util.INTENT_CODES
import com.example.recipiebook.util.RecipeSearchResultListAdapter

class SearchResultActivity : AppCompatActivity() {

    lateinit var viewModel: SpoonacularViewModel

    var recipeList: ArrayList<RecipeSearchItem> = ArrayList()

    var searchQuery = ""
    var searchDiet = ""
    var searchCuisine = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        searchQuery = intent!!.extras!!.getString("query").toString()
        searchDiet = intent!!.extras!!.getString("diet") ?: ""
        searchCuisine = intent!!.extras!!.getString("cuisine").toString() ?: ""
    }

    override fun onStart() {
        super.onStart()

        viewModel = ViewModelProvider(this).get(SpoonacularViewModel::class.java)

        var resultListAdapter = RecipeSearchResultListAdapter(::gotoDetail, recipeList)
        findViewById<RecyclerView>(R.id.recipeSearchResultListContainer).adapter = resultListAdapter
        findViewById<RecyclerView>(R.id.recipeSearchResultListContainer).layoutManager = LinearLayoutManager(this)

        viewModel!!.recipeSearchResponse.observe(this, Observer {
            println(it.toString())
            recipeList.clear()
            recipeList.addAll(it.results)
            resultListAdapter.notifyDataSetChanged()
        })

        viewModel.getRecipiesForQuery(searchQuery, searchDiet, searchCuisine)
    }

    fun gotoDetail(itemId: Int) {
        val i = Intent(this, RecipeDetailActivity::class.java)
        i.putExtra("id", itemId.toString())
        startActivityForResult(i, INTENT_CODES.SEE_RECIPE_DETAIL_INTENT.ordinal)
    }
}
