package com.example.recipiebook.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recipiebook.R
import com.example.recipiebook.util.ContentTabAdapter
import com.example.recipiebook.util.LoginTabAdapter
import kotlinx.android.synthetic.main.activity_content.*

class ContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        val tabAdapter = ContentTabAdapter(supportFragmentManager)
        frag_view.adapter = tabAdapter

        tab_bar.setupWithViewPager(frag_view)
    }
}
