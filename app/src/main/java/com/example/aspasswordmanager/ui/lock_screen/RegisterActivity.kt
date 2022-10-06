package com.example.aspasswordmanager.ui.lock_screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.Slide
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.example.aspasswordmanager.MainActivity
import com.example.aspasswordmanager.R
import com.example.aspasswordmanager.utility.Constants
import com.example.aspasswordmanager.utility.Encryption
import com.google.android.material.textfield.TextInputLayout


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = applicationContext.getSharedPreferences(Constants.PASSWORD_STORE,Context.MODE_PRIVATE)
        val password= sharedPref.getString(Constants.PASSWORD,Constants.NOT_EXIST)
        if(!password.equals(Constants.NOT_EXIST)){
            val intent = Intent(this, LoginActivity::class.java)
            val optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this)
            startActivity(intent,optionsCompat.toBundle())
            finish()
        }

        setContentView(R.layout.activity_register)
        val setButton: Button = findViewById(R.id.registerButton)
        val passwordTV: EditText = findViewById(R.id.passwordTV)
        val passwordTVHolder: TextInputLayout = findViewById(R.id.passwordTVHolder)

        

        passwordTV.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                passwordTVHolder.error=null
            }
            override fun afterTextChanged(s: Editable) {

            }
        })
        setButton.setOnClickListener {
            if (passwordTV.text.trim().length > 4) {

                val sharedPref = applicationContext.getSharedPreferences(Constants.PASSWORD_STORE,Context.MODE_PRIVATE)
                val editor=sharedPref.edit()
                val key=(1..99999999).random()
                editor.putString(Constants.PASSWORD, Encryption.encrypt(passwordTV.text.trim().toString(),key))
                editor.putInt(Constants.USER_KEY,key)
                editor.apply()
                Toast.makeText(this,"Password Set Successfully", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                passwordTVHolder.error = "Password length should be greater than 4"
            }
        }
    }
}