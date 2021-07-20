package com.example.aspasswordmanager.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.aspasswordmanager.MainActivity
import com.example.aspasswordmanager.R
import com.example.aspasswordmanager.ui.home.database.PasswordEntity

class AddItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        val save_btn: Button =findViewById(R.id.save_btn)
        val title: EditText =findViewById(R.id.title)
        val username: EditText=findViewById(R.id.username)
        val password: EditText=findViewById(R.id.password)
        val website: EditText=findViewById(R.id.website)
        val note: EditText=findViewById(R.id.note)

        val viewModel:PasswordViewModel=
                ViewModelProvider(this,PasswordViewModelFactory(this)).get(PasswordViewModel::class.java)
        save_btn.setOnClickListener(View.OnClickListener {
            val title_txt: String= title.text.toString()
            val password_txt: String= password.text.toString()
            val username_txt: String= username.text.toString()
            val website_txt: String= website.text.toString()
            val note_txt: String= note.text.toString()

            val item:PasswordEntity=PasswordEntity(title_txt,username_txt,password_txt,website_txt,note_txt)

            viewModel.insert(item)
            finish()
        })

    }
}