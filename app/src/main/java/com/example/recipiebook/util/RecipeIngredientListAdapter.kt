package com.example.recipiebook.util

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipiebook.R
import com.example.recipiebook.data.SpoonacularIngredient

class RecipeIngredientListViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_ingredient_list, parent, false)) {

    private val title : TextView
    private val amount : TextView
    private var parent: ViewGroup

    init {
        title = itemView.findViewById(R.id.name)
        amount = itemView.findViewById(R.id.amount)
        this.parent = parent
    }

    fun bind(recipe: SpoonacularIngredient) {
        title.text = recipe.name
        amount.text = "${recipe.amount} ${recipe.unit}"
    }

}

//create the listener for the recycler view
class RecipeIngredientListAdapter(private val list: ArrayList<SpoonacularIngredient>?)
    : RecyclerView.Adapter<RecipeIngredientListViewHolder>() {
    private var ingredients : ArrayList<SpoonacularIngredient>? = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeIngredientListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecipeIngredientListViewHolder(inflater, parent)
    }

    //bind the object
    override fun onBindViewHolder(holder: RecipeIngredientListViewHolder, position: Int) {
        val event: SpoonacularIngredient = ingredients!!.get(position)
        holder.bind(event)
    }

    //set the count
    override fun getItemCount(): Int = list!!.size

}