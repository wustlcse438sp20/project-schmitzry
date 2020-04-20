package com.example.recipiebook.util

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipiebook.R
import com.example.recipiebook.data.RecipeIngredient

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

    //TODO 1.0 and already has s
    fun bind(recipe: RecipeIngredient) {
        title.text = recipe.name.capitalize()
        val amt = String.format("%.2f", recipe.amount.toDouble())
        if (amt.toDouble() <= 1.0 || recipe.unit == "" || recipe.unit.last() == 's') {

            amount.text = "${amt} ${recipe.unit}"
        } else {
            amount.text = "${amt} ${recipe.unit}s"
        }

    }

}

//create the listener for the recycler view
class RecipeIngredientListAdapter(private val list: ArrayList<RecipeIngredient>?)
    : RecyclerView.Adapter<RecipeIngredientListViewHolder>() {
    private var ingredients : ArrayList<RecipeIngredient>? = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeIngredientListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecipeIngredientListViewHolder(inflater, parent)
    }

    //bind the object
    override fun onBindViewHolder(holder: RecipeIngredientListViewHolder, position: Int) {
        val event: RecipeIngredient = ingredients!!.get(position)
        holder.bind(event)
    }

    //set the count
    override fun getItemCount(): Int = list!!.size

}