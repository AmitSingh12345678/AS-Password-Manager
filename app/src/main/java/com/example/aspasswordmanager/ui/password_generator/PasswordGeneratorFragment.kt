package com.example.aspasswordmanager.ui.password_generator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.aspasswordmanager.R

class PasswordGeneratorFragment : Fragment() {

    private lateinit var mPasswordGeneratorViewModel: PasswordGeneratorViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mPasswordGeneratorViewModel =
                ViewModelProvider(this).get(PasswordGeneratorViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_password_manager, container, false)
        val textView: TextView = root.findViewById(R.id.text_password_generator)
        mPasswordGeneratorViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}