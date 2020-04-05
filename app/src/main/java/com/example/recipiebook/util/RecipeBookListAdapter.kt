package com.example.recipiebook.util

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipiebook.R
import com.example.recipiebook.data.RecipeBook

class RecpieBooksViewHolder(resolver: (String) -> Unit, inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_recipe_book_list, parent, false)) {

    private val title : TextView
    private val count: TextView
    private val button : Button
    private var resolver: (String) -> Unit = resolver
    private var parent: ViewGroup

    init {
        title = itemView.findViewById(R.id.name)
        count = itemView.findViewById(R.id.recipeCount)
        button = itemView.findViewById(R.id.viewRecipeBookButton)
        this.parent = parent
    }

    fun bind(recipe: RecipeBook) {
        title.text = recipe.name
        count.text = "${recipe.count}"

        button.setOnClickListener {
            resolver(recipe.id)
        }
    }

}

//create the listener for the recycler view
class RecipeBookListAdapter(private val resolver: (String) -> Unit, private val list: ArrayList<RecipeBook>?)
    : RecyclerView.Adapter<RecpieBooksViewHolder>() {
    private var listRecipeBooks : ArrayList<RecipeBook>? = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecpieBooksViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecpieBooksViewHolder(resolver, inflater, parent)
    }

    //bind the object
    override fun onBindViewHolder(holder: RecpieBooksViewHolder, position: Int) {
        val event: RecipeBook = listRecipeBooks!!.get(position)
        holder.bind(event)
    }

    //set the count
    override fun getItemCount(): Int = list!!.size

}