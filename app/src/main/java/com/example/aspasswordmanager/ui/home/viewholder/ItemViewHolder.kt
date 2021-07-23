package com.example.aspasswordmanager.ui.home.viewholder

import android.graphics.Color
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
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
        val colorList= listOf<Int>(R.color.md_green_500,R.color.md_pink_500,R.color.md_red_500,
                R.color.md_deep_orange_500,R.color.md_light_blue_500, R.color.md_light_green_500,
                R.color.purple_500,R.color.accent, R.color.primary_dark,R.color.md_blue_500)
        val color=ContextCompat.getColor(title.context,colorList.random())
       val drawable: TextDrawable = TextDrawable.builder().beginConfig().width(30).height(30).endConfig()
                .buildRound(item.title[0].toUpperCase().toString(),color)
       avater_image.setImageDrawable(drawable)

    }
}