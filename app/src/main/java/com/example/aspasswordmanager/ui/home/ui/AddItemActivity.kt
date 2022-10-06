package com.example.aspasswordmanager.ui.home.ui

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.transition.Slide
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.aspasswordmanager.R
import com.example.aspasswordmanager.ui.home.database.PasswordEntity
import com.example.aspasswordmanager.ui.home.viewmodel.PasswordViewModel
import com.example.aspasswordmanager.ui.home.viewmodel.PasswordViewModelFactory
import com.example.aspasswordmanager.utility.Constants
import com.example.aspasswordmanager.utility.Encryption


class AddItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        val sharedPref = applicationContext.getSharedPreferences(Constants.PASSWORD_STORE,
            Context.MODE_PRIVATE)
        val KEY=sharedPref.getInt(Constants.USER_KEY,Constants.DEFAULT_KEY)

        val  slide= Slide()
        slide.duration=150
        slide.excludeTarget(android.R.id.statusBarBackground, true)
        slide.excludeTarget(android.R.id.navigationBarBackground, true)

        window.enterTransition = slide
        window.exitTransition = slide
        val save_btn: ImageButton = findViewById(R.id.save_btn)
        val title: EditText = findViewById(R.id.title)
        val username: EditText = findViewById(R.id.username)
        val password: EditText = findViewById(R.id.password)
        val website: EditText = findViewById(R.id.website)
        val note: EditText = findViewById(R.id.note)
        val back_btn: ImageButton = findViewById(R.id.back_btn)

        password.typeface = Typeface.MONOSPACE
        note.setMultiLineCapSentencesAndDoneAction()
        var originalItem: PasswordEntity?=null
        if(intent.getSerializableExtra(Constants.ITEM_INFO)!=null) {
            originalItem = intent.getSerializableExtra(Constants.ITEM_INFO) as PasswordEntity
        }
        val msg: String? = intent.getStringExtra(Constants.MSG)

        back_btn.setOnClickListener {
            finish()
        }

        if (msg.equals(Constants.FOR_EDIT) && originalItem!=null) {
            title.setText(Encryption.decrypt(originalItem.title,KEY))
            username.setText(Encryption.decrypt(originalItem.username,KEY))
            password.setText(Encryption.decrypt(originalItem.password,KEY))
            website.setText(Encryption.decrypt(originalItem.website,KEY))
            note.setText(Encryption.decrypt(originalItem.note,KEY))
        }


        val viewModel: PasswordViewModel =
                ViewModelProvider(this, PasswordViewModelFactory(this)).get(PasswordViewModel::class.java)

        save_btn.setOnClickListener(View.OnClickListener {
            val title_txt: String = title.text.toString().trim()
            val password_txt: String = password.text.toString().trim()
            val username_txt: String = username.text.toString().trim()
            val website_txt: String = website.text.toString().trim()
            val note_txt: String = note.text.toString().trim()

            if (TextUtils.isEmpty(title_txt)) {
                title.error = "This Field can't be empty."
            } else {
                val item = PasswordEntity(
                    Encryption.encrypt(title_txt,KEY),
                    Encryption.encrypt(username_txt,KEY),
                        Encryption.encrypt(password_txt,KEY),
                            Encryption.encrypt(website_txt,KEY),
                                Encryption.encrypt(note_txt,KEY)
                )
                if (originalItem != null) item.id = originalItem.id
                if (msg.equals(Constants.FOR_EDIT)) {
                    viewModel.update(item)
                } else {
                    viewModel.insert(item)
                }
                finish()
            }
        })

    }
    // To use this, do NOT set inputType on the EditText in the layout
    private fun EditText.setMultiLineCapSentencesAndDoneAction() {
        imeOptions = EditorInfo.IME_ACTION_DONE
        setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES or InputType.TYPE_TEXT_FLAG_MULTI_LINE)
    }
}