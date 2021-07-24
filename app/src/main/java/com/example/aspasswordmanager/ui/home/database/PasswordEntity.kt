package com.example.aspasswordmanager.ui.home.database

import androidx.core.content.ContextCompat
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.aspasswordmanager.R
import java.io.Serializable

@Entity(tableName = "password_table")
 data class PasswordEntity(val title: String, val username:String, val password:String,
                           val website:String, val note:String): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @Ignore private val colorList= listOf<Int>(R.color.md_green_500, R.color.md_pink_500, R.color.md_red_500,
            R.color.md_deep_orange_500, R.color.md_light_blue_500, R.color.md_light_green_500,
            R.color.purple_500, R.color.accent, R.color.primary_dark, R.color.md_blue_500)
     var colorId: Int=colorList.random()
}