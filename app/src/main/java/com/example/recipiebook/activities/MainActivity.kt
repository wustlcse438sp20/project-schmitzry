package com.example.recipiebook.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recipiebook.R
import com.example.recipiebook.util.LoginTabAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabAdapter = LoginTabAdapter(supportFragmentManager)
        frag_view.adapter = tabAdapter

        tab_bar.setupWithViewPager(frag_view)

        mAuth = FirebaseAuth.getInstance()

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth?.currentUser
        println(currentUser)

        if (currentUser != null) {
            // Already logged in, go to game
            launchApp()
        }
    }

    fun launchApp() {
        val i = Intent(this, ContentActivity::class.java)
        val currentUser = mAuth?.currentUser
        i.putExtra("uid", currentUser!!.uid)
        startActivityForResult(i, 0)
    }
}