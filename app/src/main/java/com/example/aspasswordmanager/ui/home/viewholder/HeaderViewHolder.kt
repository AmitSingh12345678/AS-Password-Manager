package com.example.aspasswordmanager.ui.home.viewholder

import android.view.ViewGroup
import android.widget.TextView
import com.example.aspasswordmanager.R
import smartadapter.viewholder.SmartViewHolder

class HeaderViewHolder<T : Any>(parentView: ViewGroup) : SmartViewHolder<T>
    (parentView, R.layout.recycle_view_header) {
    private val header: TextView = itemView.findViewById(R.id.header_title)

    override fun bind(item: T) {
        header.text = item.toString()
    }
}