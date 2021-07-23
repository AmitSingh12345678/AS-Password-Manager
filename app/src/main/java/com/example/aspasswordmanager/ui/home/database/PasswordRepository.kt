package com.example.aspasswordmanager.ui.home.database

import android.app.DownloadManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class PasswordRepository(private val passwordDao: PasswordDao) {
    private  val TAG = "PasswordRepository"
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords: LiveData<List<PasswordEntity>> = passwordDao.getAllPasswords()

    fun searchPasswords(searchQuery: String): LiveData<List<PasswordEntity>>{
        return passwordDao.searchPasswords(searchQuery)
    }

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(passwordEntity: PasswordEntity) {
        passwordDao.insert(passwordEntity)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(passwordEntity: PasswordEntity) {
        passwordDao.delete(passwordEntity)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(passwordEntity: PasswordEntity) {
        Log.d(TAG, "update: function called with item: $passwordEntity and ${passwordEntity.id}")
        passwordDao.update(passwordEntity)
    }
}