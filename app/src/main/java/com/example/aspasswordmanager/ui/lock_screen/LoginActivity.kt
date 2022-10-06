package com.example.aspasswordmanager.ui.lock_screen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.Slide
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityOptionsCompat
import com.example.aspasswordmanager.MainActivity
import com.example.aspasswordmanager.R
import com.example.aspasswordmanager.utility.Constants
import com.example.aspasswordmanager.utility.Encryption
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton: Button = findViewById(R.id.loginButton)
        val passwordTV: EditText=findViewById(R.id.passwordTV)
        val passwordTVHolder: TextInputLayout=findViewById(R.id.passwordTVHolder)

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

        loginButton.setOnClickListener{
            val sharedPref = applicationContext.getSharedPreferences(Constants.PASSWORD_STORE,Context.MODE_PRIVATE)
            val enteredPassword: String=passwordTV.text.toString()
            val key=sharedPref.getInt(Constants.USER_KEY,Constants.DEFAULT_KEY)
            val password= sharedPref.getString(Constants.PASSWORD,Constants.NOT_EXIST)?.let {
                Encryption.decrypt(
                    it,key)
            }
            Log.d(TAG, "onCreate: $password >>>>>> $enteredPassword")
            if(enteredPassword == password){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                    passwordTVHolder.error="Wrong Password"
            }
        }
    }
}