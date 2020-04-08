package com.example.recipiebook.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipiebook.R
import com.example.recipiebook.data.RecipeSearchItem
import com.squareup.picasso.Picasso

class RecipeSearchResultItemViewHolder(resolver: (Int) -> Unit, inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_search_result, parent, false)) {

    private val title : TextView
    private val cookTime: TextView
    private val button : Button
    private val image: ImageView
    private var resolver: (Int) -> Unit = resolver
    private var parent: ViewGroup

    init {
        title = itemView.findViewById(R.id.name)
        cookTime = itemView.findViewById(R.id.cookTime)
        button = itemView.findViewById(R.id.viewRecipeButton)
        image = itemView.findViewById(R.id.recipeImage)
        this.parent = parent
    }

    fun bind(recipe: RecipeSearchItem) {
        title.text = recipe.title
        cookTime.text = "${recipe.readyInMinutes} min"

        println(recipe.image)

        if (recipe.image != null) {
            Picasso.get().load("https://spoonacular.com/recipeImages/${recipe.image}").into(image)
        } else {
            image.visibility = View.GONE
        }

        button.setOnClickListener {
            resolver(recipe.id)
        }
    }

}

//create the listener for the recycler view
class RecipeSearchResultListAdapter(private val resolver: (Int) -> Unit, private val list: ArrayList<RecipeSearchItem>?)
    : RecyclerView.Adapter<RecipeSearchResultItemViewHolder>() {
    private var listRecipes : ArrayList<RecipeSearchItem>? = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeSearchResultItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecipeSearchResultItemViewHolder(resolver, inflater, parent)
    }

    //bind the object
    override fun onBindViewHolder(holder: RecipeSearchResultItemViewHolder, position: Int) {
        val event: RecipeSearchItem = listRecipes!!.get(position)
        holder.bind(event)
    }

    //set the count
    override fun getItemCount(): Int = list!!.size

}