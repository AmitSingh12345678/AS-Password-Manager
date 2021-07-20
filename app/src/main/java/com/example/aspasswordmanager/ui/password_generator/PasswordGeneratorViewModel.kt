package com.example.aspasswordmanager.ui.password_generator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PasswordGeneratorViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is password generator Fragment"
    }
    val text: LiveData<String> = _text
}