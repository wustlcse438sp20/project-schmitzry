package com.example.recipiebook.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.recipiebook.R
import com.example.recipiebook.activities.ContentActivity
import com.example.recipiebook.data.FirebaseRecipeBook
import com.example.recipiebook.data.RecipeBookRef
import com.example.recipiebook.data.UserData
import com.example.recipiebook.util.RecipeBookListAdapter
import com.example.recipiebook.util.RecipeSearchResultListAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.fragment_recipe_book_list.*
import kotlinx.android.synthetic.main.modal_create_recipe_book.*
import kotlinx.android.synthetic.main.modal_create_recipe_book.view.*
import java.util.*

class RecipeBookListFragment : Fragment() {

    var bookList: ArrayList<RecipeBookRef> = ArrayList()
    lateinit var uData: UserData

    lateinit var resultListAdapter: RecipeBookListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_book_list, container, false)
    }

    override fun onStart() {
        super.onStart()

        resultListAdapter = RecipeBookListAdapter((activity!! as ContentActivity)::gotoBook, bookList)
        recipeBookListContainer.adapter = resultListAdapter
        recipeBookListContainer.layoutManager = LinearLayoutManager(activity!!)

        logout.setOnClickListener {
            (activity!! as ContentActivity).mAuth!!.signOut()
            (activity!! as ContentActivity).logout()
        }


        createBook.setOnClickListener {
            dialogView()
        }

        getBooks()
    }

    private fun dialogView() {

        val dialogView = LayoutInflater.from(activity).inflate(R.layout.modal_create_recipe_book, null)
        val mBuilder = AlertDialog.Builder(activity)
            .setView(dialogView)
        val mAlertDialog = mBuilder.show()

        // Sets an onclick listener to Create Button and check values
        mAlertDialog.createBookButton.setOnClickListener {
            val newName = dialogView.newBookName.text.toString()

            if (newName == ""){
                val myToast = Toast.makeText(activity, "Please enter a valid name for your new book", Toast.LENGTH_SHORT)
                myToast.show()
            } else {
                val guid = UUID.randomUUID().toString()
                uData.books.add(RecipeBookRef(newName, guid))
                (activity!! as ContentActivity).db.collection("Users").document((activity!! as ContentActivity).uid!!).set(uData)

                val book = FirebaseRecipeBook(newName, ArrayList())
                (activity!! as ContentActivity).db.collection("Books").document(guid).set(book)
                getBooks()

                mAlertDialog.dismiss()
            }
        }
    }

    private fun getBooks() {

        (activity!! as ContentActivity).db.collection("Users").document((activity!! as ContentActivity).uid!!).get().addOnSuccessListener { item ->

            val res = item.toObject(UserData::class.java)

            uData = res!!

            bookList.clear()

            for (b in res!!.books) {
                bookList.add(b)
            }

            resultListAdapter.notifyDataSetChanged()

        }

    }
}
