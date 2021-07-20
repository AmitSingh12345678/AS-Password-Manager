package com.example.aspasswordmanager.ui.home

import android.view.ViewGroup
import android.widget.TextView
import com.example.aspasswordmanager.R
import org.w3c.dom.Text
import smartadapter.viewholder.SmartViewHolder

class HeaderViewHolder<T: Any>(parentView: ViewGroup): SmartViewHolder<T>
    (parentView , R.layout.recycle_view_header) {
    private val header:TextView=itemView.findViewById(R.id.header_title)
//    init {
//        itemView.setBackgroundColor(color)
//    }
//
//    companion object {
//        var color = (Math.random() * 16777215).toInt() or (0xFF shl 24)
//    }

    override fun bind(item: T) {
        header.text=item.toString()
    }
}