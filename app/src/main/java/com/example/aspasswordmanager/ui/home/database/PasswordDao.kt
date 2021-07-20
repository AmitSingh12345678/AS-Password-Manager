package com.example.aspasswordmanager.ui.home.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PasswordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(passwordEntity: PasswordEntity)

    @Delete
    suspend fun delete(passwordEntity: PasswordEntity)

    @Update
    suspend fun update(passwordEntity: PasswordEntity)


    // Room automatically run those fun on bg threads, who are returning an flow
    @Query(" SELECT * from  password_table ORDER BY title ASC;")
    fun getAllPasswords(): LiveData<List<PasswordEntity>>
}