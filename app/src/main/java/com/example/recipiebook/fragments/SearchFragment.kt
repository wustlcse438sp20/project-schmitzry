package com.example.recipiebook.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.recipiebook.R
import com.example.recipiebook.activities.SearchResultActivity
import com.example.recipiebook.util.INTENT_CODES
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onStart() {
        super.onStart()

        searchButton.setOnClickListener {
            val text = searchTextInput.text.toString()
            val exclude = excludeFilter.text.toString()

            var cuisineFilter = ""

            if (italianFilter.isChecked) {
                cuisineFilter += "italian,"
            }

            if (americanFilter.isChecked) {
                cuisineFilter += "american,"
            }

            if (frenchFilter.isChecked) {
                cuisineFilter += "french,"
            }

            if (latinAmericanFilter.isChecked) {
                cuisineFilter += "latinAmerican,"
            }

            var dietFilter = ""

            if (vegetarianFilter.isChecked) {
                dietFilter += "vegetarian,"
            }

            if (veganFilter.isChecked) {
                dietFilter += "vegan,"
            }

            if (ketoFilter.isChecked) {
                dietFilter += "ketogenic,"
            }

            if (paleoFilter.isChecked) {
                dietFilter += "paleo,"
            }

            if (text != "") {

                val i = Intent(activity, SearchResultActivity::class.java)
                i.putExtra("query", text)
                i.putExtra("cuisine", cuisineFilter)
                i.putExtra("diet", dietFilter)
                i.putExtra("exclude", exclude)
                startActivityForResult(i, INTENT_CODES.SEE_RECIPE_SEARCH_RESULT_INTENT.ordinal)

            } else {
                val myToast = Toast.makeText(activity, "Please enter a valid search query", Toast.LENGTH_SHORT)
                myToast.show()
            }
        }
    }
}
