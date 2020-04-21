package com.example.recipiebook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.recipiebook.activities.MainActivity

import com.example.recipiebook.R
import com.example.recipiebook.data.UserData
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onStart() {
        super.onStart()

        signUpButton.setOnClickListener {
            signUp()
        }

    }

    private fun signUp() {

        val email = signUpEmail.text.toString()

        val password = signUpPassword.text.toString()

       // val username = signUpName.text.toString()

        // TODO make new book

        if (password == "" || email == "") {
            Toast.makeText(
                (activity!! as MainActivity), "One of your email, username, or password was blank. Please enter a valid username / email / password",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        (activity!! as MainActivity).mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {

                    val uData = UserData()

                    (activity!! as MainActivity).db.collection("Users").document((activity!! as MainActivity).mAuth!!.currentUser!!.uid).set(uData)

                    (activity!! as MainActivity).launchApp()

                } else {

                    Toast.makeText(
                        (activity!! as MainActivity), "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }

}
