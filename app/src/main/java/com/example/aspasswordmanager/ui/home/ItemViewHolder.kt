package com.example.aspasswordmanager.ui.home

import android.graphics.Color
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import com.example.aspasswordmanager.R
import com.example.aspasswordmanager.ui.home.database.PasswordEntity
import smartadapter.viewholder.SmartViewHolder


class ItemViewHolder(parentView: ViewGroup):
SmartViewHolder<PasswordEntity>(parentView, R.layout.recycle_view_item){

    private var title:TextView=itemView.findViewById(R.id.title)
    private var avater_image:ImageView=itemView.findViewById(R.id.avatar_image)
    lateinit var item:PasswordEntity

    override fun bind(item: PasswordEntity) {
        this.item =item
        title.text=item.title
       val color: Int=Color.argb(255, (30..200).random(),  (30..200).random(),  (30..200).random());
       val drawable: TextDrawable = TextDrawable.builder().beginConfig().width(30).height(30).endConfig()
                .buildRound(item.title[0].toUpperCase().toString(),color)
       avater_image.setImageDrawable(drawable)

    }
}