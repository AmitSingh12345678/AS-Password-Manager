package com.example.aspasswordmanager.ui.home.viewholder

import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ImageView
import android.widget.TextView
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
        val color=ContextCompat.getColor(title.context, item.colorId)
        val drawable: TextDrawable = TextDrawable.builder().beginConfig().width(30).height(30).endConfig()
                .buildRound(item.title[0].toUpperCase().toString(), color)
       avater_image.setImageDrawable(drawable)

    }
}