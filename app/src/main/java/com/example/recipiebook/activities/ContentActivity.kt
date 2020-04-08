package com.example.recipiebook.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recipiebook.R
import com.example.recipiebook.util.ContentTabAdapter
import com.example.recipiebook.util.LoginTabAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_content.*

class ContentActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null

    var uid: String? = null

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        val tabAdapter = ContentTabAdapter(supportFragmentManager)
        frag_view.adapter = tabAdapter

        tab_bar.setupWithViewPager(frag_view)

        mAuth = FirebaseAuth.getInstance()

        uid = intent!!.extras!!.getString("uid")
    }

    fun logout() {
        setResult(Activity.RESULT_OK, Intent())
        finish()
    }

    fun gotoBook(id: String) {
        val i = Intent(this, RecipeBookContentsActivity::class.java)
        i.putExtra("bookId", id)
        startActivityForResult(i, 0)
    }
}
