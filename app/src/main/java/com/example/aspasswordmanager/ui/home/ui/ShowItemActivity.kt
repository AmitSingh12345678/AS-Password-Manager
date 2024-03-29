package com.example.aspasswordmanager.ui.home.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.KeyEvent
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.amulyakhare.textdrawable.TextDrawable
import com.example.aspasswordmanager.R
import com.example.aspasswordmanager.ui.home.database.PasswordEntity
import com.example.aspasswordmanager.ui.home.viewmodel.PasswordViewModel
import com.example.aspasswordmanager.ui.home.viewmodel.PasswordViewModelFactory
import com.example.aspasswordmanager.utility.Constants
import com.example.aspasswordmanager.utility.Encryption

class ShowItemActivity : AppCompatActivity() {
    private val TAG = "ShowItemActivity"
    private lateinit var title:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_item)

        val  slide= Slide()
        slide.duration=150
        slide.excludeTarget(android.R.id.statusBarBackground, true)
        slide.excludeTarget(android.R.id.navigationBarBackground, true)

        window.enterTransition = slide
        window.exitTransition = slide

        val sharedPref = applicationContext.getSharedPreferences(Constants.PASSWORD_STORE,
            Context.MODE_PRIVATE)
        val KEY=sharedPref.getInt(Constants.USER_KEY,Constants.DEFAULT_KEY)
        val item: PasswordEntity= intent.getSerializableExtra(Constants.ITEM_INFO) as PasswordEntity

        title =findViewById(R.id.title)
        val username: TextView =findViewById(R.id.username)
        val password: TextView =findViewById(R.id.password)
        val website: TextView =findViewById(R.id.website)
        val note: TextView =findViewById(R.id.note)
        val back_btn:ImageButton=findViewById(R.id.back_btn)
        val edit_btn:ImageButton=findViewById(R.id.edit_btn)
        val deleteBtn: ImageButton=findViewById(R.id.delete_btn)
        val avatarImage: ImageView=findViewById(R.id.avatar_image)

        title.text = Encryption.decrypt(item.title,KEY)
        username.text = Encryption.decrypt(item.username,KEY)
        password.text = Encryption.decrypt(item.password,KEY)
        website.text = Encryption.decrypt(item.website,KEY)
        note.text = Encryption.decrypt(item.note,KEY)

        val color=ContextCompat.getColor(this,item.colorId)
        val drawable: TextDrawable = TextDrawable.builder().beginConfig().width(30).height(30).endConfig()
                .buildRound(title.text[0].toUpperCase().toString(),color)
        avatarImage.setImageDrawable(drawable)
        password.typeface = Typeface.MONOSPACE

        back_btn.setOnClickListener {
            finish()
        }
        edit_btn.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            intent.putExtra(Constants.ITEM_INFO, item)
            intent.putExtra(Constants.MSG,Constants.FOR_EDIT)
            startActivity(intent)
            finish()
        }
        deleteBtn.setOnClickListener{
            Log.d(TAG, "onCreate: Delete Button pressed")
            val viewModel: PasswordViewModel =
                    ViewModelProvider(this, PasswordViewModelFactory(this)).get(PasswordViewModel::class.java)
            val dialogBuilder: AlertDialog.Builder=AlertDialog.Builder(this)
            dialogBuilder.setMessage(getString(R.string.alert_dialog_delete_message)).setTitle(getString(R.string.alert_dialog_delete_title))

            dialogBuilder.setCancelable(false)
                    .setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                          viewModel.delete(item)
                          finish()
                    }
                    .setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
                        dialogInterface.cancel()
                    }
            val dialogBox=dialogBuilder.create()
            dialogBox.show()
        }


    }

}