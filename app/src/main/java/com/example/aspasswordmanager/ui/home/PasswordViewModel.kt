package com.example.aspasswordmanager.ui.home

import android.content.Context
import androidx.lifecycle.*
import com.example.aspasswordmanager.ui.home.database.PasswordDao
import com.example.aspasswordmanager.ui.home.database.PasswordDatabase
import com.example.aspasswordmanager.ui.home.database.PasswordEntity
import com.example.aspasswordmanager.ui.home.database.PasswordRepository
import kotlinx.coroutines.launch


class PasswordViewModel(context: Context) : ViewModel() {

    val repository:PasswordRepository

    init{
        val database: PasswordDatabase = PasswordDatabase.getDatabase(context)
        val dao: PasswordDao =database.passwordDao()
        repository= PasswordRepository(dao)
    }

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<PasswordEntity>> = repository.allWords

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(passwordEntity: PasswordEntity) = viewModelScope.launch {
        repository.insert(passwordEntity)
    }
    fun delete(passwordEntity: PasswordEntity)=viewModelScope.launch {
        repository.delete(passwordEntity)
    }
    fun update(passwordEntity: PasswordEntity)=viewModelScope.launch {
        repository.update(passwordEntity)
    }
}

// to pass parameter in constructor of viewModel, we have to create an custom constructor
class PasswordViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PasswordViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}