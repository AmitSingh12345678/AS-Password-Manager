package com.example.aspasswordmanager.ui.home.viewholder

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.amulyakhare.textdrawable.TextDrawable
import com.example.aspasswordmanager.R
import com.example.aspasswordmanager.ui.home.database.PasswordEntity
import com.example.aspasswordmanager.utility.Encryption
import smartadapter.viewholder.SmartViewHolder
import java.util.*


class ItemViewHolder(parentView: ViewGroup) :
    SmartViewHolder<PasswordEntity>(parentView, R.layout.recycle_view_item) {

    private var title: TextView = itemView.findViewById(R.id.title)
    private var avater_image: ImageView = itemView.findViewById(R.id.avatar_image)
    lateinit var item: PasswordEntity

    override fun bind(item: PasswordEntity) {

        val sharedPref = title.context.applicationContext.getSharedPreferences(
            "PASSWORD_STORE",
            Context.MODE_PRIVATE
        )
        val KEY = sharedPref.getInt("KEY", 1000)
        this.item = item
        title.text = Encryption.decrypt(item.title, KEY)


        val color = ContextCompat.getColor(title.context, item.colorId)
        val drawable: TextDrawable =
            TextDrawable.builder().beginConfig().width(30).height(30).endConfig()
                .buildRound(title.text[0].toString().toUpperCase(Locale.ROOT), color)
        avater_image.setImageDrawable(drawable)

    }
}