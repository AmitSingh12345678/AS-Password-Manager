package com.example.aspasswordmanager.ui.settings

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.aspasswordmanager.R
import com.example.aspasswordmanager.utility.Constants
import com.example.aspasswordmanager.utility.Encryption
import com.google.android.material.textfield.TextInputLayout

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val currPasswordTV: EditText = findViewById(R.id.password1TV)
        val currPasswordTVHolder: TextInputLayout = findViewById(R.id.password1TVHolder)
        val newPasswordTV: EditText = findViewById(R.id.password2TV)
        val newPasswordTVHolder: TextInputLayout = findViewById(R.id.password2TVHolder)
        val changeButton: Button =findViewById(R.id.changeButton)
        val backButton: ImageButton=findViewById(R.id.back_btn)

        currPasswordTV.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                currPasswordTVHolder.error=null
            }
            override fun afterTextChanged(s: Editable) {

            }
        })
        newPasswordTV.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                newPasswordTVHolder.error=null
            }
            override fun afterTextChanged(s: Editable) {

            }
        })
        changeButton.setOnClickListener {
            val sharedPref = applicationContext.getSharedPreferences(Constants.PASSWORD_STORE, Context.MODE_PRIVATE)
            val key=sharedPref.getInt(Constants.USER_KEY, Constants.DEFAULT_KEY)
            val currPassword= sharedPref.getString(Constants.PASSWORD, Constants.NOT_EXIST)?.let {
                Encryption.decrypt(
                    it,key)
            }
            if(currPassword.equals(currPasswordTV.text.toString())) {
                if (newPasswordTV.text.trim().length > 4) {
                    val editor = sharedPref.edit()
                    val key=sharedPref.getInt(Constants.USER_KEY,Constants.DEFAULT_KEY)
                    editor.putString(Constants.PASSWORD, Encryption.encrypt(newPasswordTV.text.trim().toString(),key))
                    editor.apply()
                    Toast.makeText(this,"Password Changed Successfully",Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    newPasswordTVHolder.error = "Password length should be greater than 4"
                }
            }else{
                currPasswordTVHolder.error=" Wrong Master Password"
            }
        }

        backButton.setOnClickListener{
            finish()
        }
    }
}