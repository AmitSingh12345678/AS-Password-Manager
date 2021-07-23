package com.example.aspasswordmanager.ui.home.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "password_table")
 data class PasswordEntity(val title: String, val username:String, val password:String,
                           val website:String, val note:String): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}