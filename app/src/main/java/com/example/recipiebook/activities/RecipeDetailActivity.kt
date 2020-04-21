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
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.recipiebook.data.*
import com.example.recipiebook.util.RecipeBookListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_recipe_book_contents.*
import kotlinx.android.synthetic.main.activity_recipe_detail.*
import kotlinx.android.synthetic.main.activity_recipe_detail.loadingBar
import kotlinx.android.synthetic.main.activity_search_result.*


class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: SpoonacularViewModel

    private lateinit var recipeItem: RecipeDetailResponse

    private var ingredientList: ArrayList<RecipeIngredient> = ArrayList()

    private var itemId = ""

    private var isSaved = false

    private var savedBookId = ""
    private var savedRecipeId = ""

    private var addToBookDialog: AlertDialog? = null

    private val db = FirebaseFirestore.getInstance()

    var mAuth = FirebaseAuth.getInstance()

    var bookList: ArrayList<RecipeBookRef> = ArrayList()

    var bookData: FirebaseRecipeBook = FirebaseRecipeBook()

    var currRecipe: FirebaseRecipeBookRecipe = FirebaseRecipeBookRecipe()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)
        itemId = intent!!.extras!!.getString("id").toString()
        isSaved = intent!!.extras!!.getString("saved") != null
        savedBookId = intent!!.extras!!.getString("bookId").toString()
        savedRecipeId = intent!!.extras!!.getString("recipeId").toString()

        loadingBar.show()
    }

    override fun onStart() {
        super.onStart()

        ingredientList.clear()

        var ingredientAdapter = RecipeIngredientListAdapter(ingredientList)
        findViewById<RecyclerView>(R.id.recipeIngredientListContainer).adapter = ingredientAdapter
        findViewById<RecyclerView>(R.id.recipeIngredientListContainer).layoutManager = LinearLayoutManager(this)

        if (!isSaved) {

            viewModel = ViewModelProvider(this).get(SpoonacularViewModel::class.java)

            // get from spoonacular
            viewModel!!.recipeDetailResponse.observe(this, Observer {

                recipeItem = it

                if (recipeItem.image != null) {
                    Picasso.get().load(recipeItem.image).into(recipeImage)
                } else {
                    recipeImage.visibility = View.GONE
                }

                ingredientList.clear()
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

                recipeNotes.visibility = View.GONE
                updateRecipeNotes.visibility = View.GONE

                loadingBar.hide()

            })

            viewModel.getRecipeDetail(itemId)
        } else {
            // get from FireStore
            db.collection("Books").document(savedBookId).get().addOnSuccessListener { item ->

                val res = item.toObject(FirebaseRecipeBook::class.java)

                bookData = res!!

                for (item in bookData.recipes) {
                    if (item.id == savedRecipeId) {
                        // found recipe!
                        currRecipe = item
                    }
                }

                // Remove so when we add it will be the modified recipe
                bookData.recipes.remove(currRecipe)

                if (currRecipe.imageUrl != null) {
                    Picasso.get().load(currRecipe.imageUrl).into(recipeImage)
                } else {
                    recipeImage.visibility = View.GONE
                }

                ingredientList.clear()
                ingredientList.addAll(currRecipe.ingredients)

                ingredientAdapter.notifyDataSetChanged()

                recipeTitle.text = currRecipe.name

                cookTime.text = currRecipe.timeToCook.toString()

                gotoRecipeOnline.setOnClickListener {
                    val uri = Uri.parse(currRecipe.webUrl)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }

                addToRecipeBook.visibility = View.GONE

                recipeNotes.setText(currRecipe.userNotes)

                updateRecipeNotes.setOnClickListener {
                    val notes = recipeNotes.text.toString()

                    currRecipe = currRecipe.copy(userNotes = notes)

                    bookData.recipes.add(currRecipe)

                    db.collection("Books").document(savedBookId).set(bookData).addOnSuccessListener {
                        Toast.makeText(
                            this, "Notes Saved",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                loadingBar.hide()

            }
        }


    }

    fun addRecipeToBook(bookId: String) {
        db.collection("Books").document(bookId).get().addOnSuccessListener { item ->

            val res = item.toObject(FirebaseRecipeBook::class.java)

            var bookDetail = res!!

            val newRecipe = FirebaseRecipeBookRecipe(id=recipeItem.id.toString(), name=recipeItem.title, imageUrl = recipeItem.image, timeToCook = recipeItem.readyInMinutes, webUrl = recipeItem.sourceUrl)

            for (item in recipeItem.extendedIngredients) {
                newRecipe.ingredients.add(RecipeIngredient(name=item.name, amount = item.amount, unit = item.unit))
            }

            bookDetail.recipes.add(newRecipe)

            db.collection("Books").document(bookId).set(bookDetail).addOnSuccessListener {
                addToBookDialog?.dismiss()
            }

        }
    }

    fun performAddToBook() {
        if (isSaved) {
            Toast.makeText(
                this, "Cannot add recipe to book that is already in a book",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            // TODO add modal

            val dialogView = LayoutInflater.from(this).inflate(R.layout.modal_select_recipe_book, null)

            var bookAdapter = RecipeBookListAdapter(::addRecipeToBook, bookList)
            dialogView.findViewById<RecyclerView>(R.id.recipeBookListContainer).adapter = bookAdapter
            dialogView.findViewById<RecyclerView>(R.id.recipeBookListContainer).layoutManager = LinearLayoutManager(this)

            val mBuilder = AlertDialog.Builder(this)
                .setView(dialogView)
            addToBookDialog = mBuilder.show()


            db.collection("Users").document(mAuth.currentUser!!.uid).get().addOnSuccessListener { item ->

                val res = item.toObject(UserData::class.java)

                //uData = res!!

                bookList.clear()

                for (b in res!!.books) {
                    bookList.add(b)
                }

                bookAdapter.notifyDataSetChanged()

                // TODO move to add handlers
                //val selectedBook = bookList[0]

            }
        }
    }
}
