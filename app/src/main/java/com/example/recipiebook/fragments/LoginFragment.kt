package com.example.recipiebook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.recipiebook.activities.MainActivity

import com.example.recipiebook.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onStart() {
        super.onStart()

        loginButton.setOnClickListener {
            login()
        }

    }

    fun login() {

        val email = loginEmail.text.toString()

        val password = loginPassword.text.toString()

        if (password == "" || email == "") {
            Toast.makeText(
                (activity!! as MainActivity), "Your email, or password was blank. Please enter a valid email / password",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        (activity!! as MainActivity).mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {

                    (activity!! as MainActivity).launchApp()

                } else {

                    println("Failed to sign in: ${task.exception}")
                    Toast.makeText(
                        (activity!! as MainActivity), "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }
}
