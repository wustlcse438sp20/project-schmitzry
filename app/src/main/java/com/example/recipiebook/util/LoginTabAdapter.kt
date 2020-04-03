package com.example.recipiebook.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.recipiebook.fragments.LoginFragment
import com.example.recipiebook.fragments.SignUpFragment

class LoginTabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount() : Int {
        return 2
    }

    override fun getItem(position: Int) : Fragment {
        return when (position) {
            0 -> { LoginFragment() }
            else -> SignUpFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Login"
            else -> "Sign Up"
        }
    }

}