package com.example.aspasswordmanager.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.aspasswordmanager.R
import com.example.aspasswordmanager.ui.home.database.PasswordEntity

class ShowItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_item)

        val item: PasswordEntity= intent.getSerializableExtra("ITEM_INFO") as PasswordEntity

        val title: TextView =findViewById(R.id.title)
        val username: TextView =findViewById(R.id.username)
        val password: TextView =findViewById(R.id.password)
        val website: TextView =findViewById(R.id.website)
        val note: TextView =findViewById(R.id.note)

        title.text=item.title
        username.text=item.username
        password.text=item.password
        website.text=item.password
        note.text=item.note

    }
}