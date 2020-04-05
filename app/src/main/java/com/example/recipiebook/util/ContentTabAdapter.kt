package com.example.recipiebook.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.recipiebook.fragments.RecipeBookListFragment
import com.example.recipiebook.fragments.SearchFragment

class ContentTabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount() : Int {
        return 2
    }

    override fun getItem(position: Int) : Fragment {
        return when (position) {
            0 -> { SearchFragment() }
            else -> RecipeBookListFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Search"
            else -> "Recipe Books"
        }
    }

}