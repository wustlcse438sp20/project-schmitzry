package com.example.recipiebook.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recipiebook.R
import com.example.recipiebook.util.ContentTabAdapter
import com.example.recipiebook.util.LoginTabAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_content.*

class ContentActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        val tabAdapter = ContentTabAdapter(supportFragmentManager)
        frag_view.adapter = tabAdapter

        tab_bar.setupWithViewPager(frag_view)

        mAuth = FirebaseAuth.getInstance()
    }

    fun logout() {
        setResult(Activity.RESULT_OK, Intent())
        finish()
    }
}
