package com.example.aspasswordmanager.ui.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.aspasswordmanager.R


class SettingsFragment : Fragment() {
    private val TAG = "SettingsFragment"
    private lateinit var mSetingsViewModel: SetingsViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mSetingsViewModel =
            ViewModelProvider(this).get(SetingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        val color = ContextCompat.getColor(requireContext(), R.color.primary)
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar!!
        actionBar.setBackgroundDrawable(ColorDrawable(color))
        requireActivity().window.statusBarColor = color

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val changePassword: RelativeLayout = view.findViewById(R.id.changePassword)
        val aboutUs: RelativeLayout = view.findViewById(R.id.aboutUs)
        val reportBug: RelativeLayout = view.findViewById(R.id.reportBug)
        val shareFeedback: RelativeLayout = view.findViewById(R.id.shareFeedback)
        changePassword.setOnClickListener(listener)
        aboutUs.setOnClickListener(listener)
        reportBug.setOnClickListener(listener)
        shareFeedback.setOnClickListener(listener)


    }

    val listener = View.OnClickListener { view ->
        when (view.id) {
            R.id.changePassword -> {
                val intent: Intent = Intent(context, ChangePasswordActivity::class.java)
                startActivity(intent)
            }
            R.id.reportBug -> {

                val subject:String="Bug Report"
                val mailto = "mailto:aspm400@gmail.com" +
                        "?subject=" + Uri.encode(subject)

                val emailIntent = Intent(Intent.ACTION_SENDTO)
                emailIntent.data = Uri.parse(mailto)

                try {
                    startActivity(emailIntent)
                } catch (e: ActivityNotFoundException) {
                    //TODO: Handle case where no email app is available
                    Toast.makeText(context,"Bug Reporting Failed",Toast.LENGTH_SHORT).show()
                }
            }
            R.id.shareFeedback -> {
                val subject:String="Sharing Feedback"
                val mailto = "mailto:aspm400@gmail.com" +
                        "?subject=" + Uri.encode(subject)

                val emailIntent = Intent(Intent.ACTION_SENDTO)
                emailIntent.data = Uri.parse(mailto)

                try {
                    startActivity(emailIntent)
                } catch (e: ActivityNotFoundException) {
                    //TODO: Handle case where no email app is available
                    Toast.makeText(context,"Bug Reporting Failed",Toast.LENGTH_SHORT).show()
                }
            }
            R.id.aboutUs -> {
                val intent: Intent = Intent(context, AboutUsActivity::class.java)
                startActivity(intent)
            }


        }
    }


}