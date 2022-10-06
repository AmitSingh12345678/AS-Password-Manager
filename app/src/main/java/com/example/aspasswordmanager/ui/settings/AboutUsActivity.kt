package com.example.aspasswordmanager.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.example.aspasswordmanager.R

class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        val backButton: ImageButton =findViewById(R.id.back_btn)

        backButton.setOnClickListener{
            finish()
        }

    }

    fun openLinkedIn ( view: View) {
        goToUrl ( "https://www.linkedin.com/in/amit-singh-5029/")
    }
    fun openGithub ( view: View) {
        goToUrl ( "https://github.com/AmitSingh12345678")
    }

    private fun goToUrl (url:String) {
        val uriUrl= Uri.parse(url)
        val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
        startActivity(launchBrowser)
    }

}