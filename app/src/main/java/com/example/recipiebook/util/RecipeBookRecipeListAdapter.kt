package com.example.recipiebook.util

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipiebook.R
import com.example.recipiebook.data.FirebaseRecipeBookRecipe
import com.example.recipiebook.data.RecipeBookRef

class RecipeBookRecipeViewHolder(resolver: (String) -> Unit, inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_recipe_book_recipe_list, parent, false)) {

    private val title : TextView
    private val button : Button
    private var resolver: (String) -> Unit = resolver
    private var parent: ViewGroup

    init {
        title = itemView.findViewById(R.id.name)
        button = itemView.findViewById(R.id.viewRecipeButton)
        this.parent = parent
    }

    fun bind(recipe: FirebaseRecipeBookRecipe) {
        title.text = recipe.name

        button.setOnClickListener {
            resolver(recipe.id)
        }
    }

}

//create the listener for the recycler view
class RecipeBookRecipeListAdapter(private val resolver: (String) -> Unit, private val list: ArrayList<FirebaseRecipeBookRecipe>?)
    : RecyclerView.Adapter<RecipeBookRecipeViewHolder>() {
    private var listRecipeBooks : ArrayList<FirebaseRecipeBookRecipe>? = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeBookRecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecipeBookRecipeViewHolder(resolver, inflater, parent)
    }

    //bind the object
    override fun onBindViewHolder(holder: RecipeBookRecipeViewHolder, position: Int) {
        val event: FirebaseRecipeBookRecipe = listRecipeBooks!!.get(position)
        holder.bind(event)
    }

    //set the count
    override fun getItemCount(): Int = list!!.size

}