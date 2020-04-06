package com.example.recipiebook.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.recipiebook.R
import com.example.recipiebook.activities.ContentActivity
import kotlinx.android.synthetic.main.fragment_recipe_book_list.*

class RecipeBookListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_book_list, container, false)
    }

    override fun onStart() {
        super.onStart()

        logout.setOnClickListener {
            (activity!! as ContentActivity).mAuth!!.signOut()
            (activity!! as ContentActivity).logout()
        }
    }
}
