package com.example.aspasswordmanager.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.aspasswordmanager.R

class SettingsFragment : Fragment() {

    private lateinit var mSetingsViewModel: SetingsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mSetingsViewModel =
                ViewModelProvider(this).get(SetingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        val textView: TextView = root.findViewById(R.id.text_settings)
        mSetingsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}