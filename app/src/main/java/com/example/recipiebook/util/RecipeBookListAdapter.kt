package com.example.recipiebook.util

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipiebook.R
import com.example.recipiebook.data.RecipeBook
import com.example.recipiebook.data.RecipeBookRef

class RecpieBooksViewHolder(resolver: (String) -> Unit, inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_recipe_book_list, parent, false)) {

    private val title : TextView
    private val button : Button
    private var resolver: (String) -> Unit = resolver
    private var parent: ViewGroup

    init {
        title = itemView.findViewById(R.id.name)
        button = itemView.findViewById(R.id.viewRecipeBookButton)
        this.parent = parent
    }

    fun bind(recipe: RecipeBookRef) {
        title.text = recipe.name

        button.setOnClickListener {
            resolver(recipe.id)
        }
    }

}

//create the listener for the recycler view
class RecipeBookListAdapter(private val resolver: (String) -> Unit, private val list: ArrayList<RecipeBookRef>?)
    : RecyclerView.Adapter<RecpieBooksViewHolder>() {
    private var listRecipeBooks : ArrayList<RecipeBookRef>? = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecpieBooksViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecpieBooksViewHolder(resolver, inflater, parent)
    }

    //bind the object
    override fun onBindViewHolder(holder: RecpieBooksViewHolder, position: Int) {
        val event: RecipeBookRef = listRecipeBooks!!.get(position)
        holder.bind(event)
    }

    //set the count
    override fun getItemCount(): Int = list!!.size

}