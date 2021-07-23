package com.example.aspasswordmanager.ui.home.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
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
        val back_btn:ImageButton=findViewById(R.id.back_btn)
        val edit_btn:ImageButton=findViewById(R.id.edit_btn)

        title.text=item.title
        username.text=item.username
        password.text=item.password
        website.text=item.password
        note.text=item.note

        back_btn.setOnClickListener {
            finish()
        }
        edit_btn.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            intent.putExtra("ITEM_INFO", item)
            intent.putExtra("MSG","FOR_EDIT")
            startActivity(intent)
            finish()
        }

    }
}