package com.example.aspasswordmanager.ui.password_generator

import android.app.ActionBar
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.aspasswordmanager.R
import com.google.android.material.slider.Slider
import com.google.android.material.switchmaterial.SwitchMaterial


class PasswordGeneratorFragment : Fragment() {
    private val TAG = "PasswordGeneratorFragme"
    private lateinit var passwordGeneratorViewModel: PasswordGeneratorViewModel
    private lateinit var digitButton: SwitchMaterial
    private lateinit var letterButton: SwitchMaterial
    private lateinit var symbolButton: SwitchMaterial
    private lateinit var generatedPassword: TextView
    private lateinit var slider: Slider
    private lateinit var screen: CardView
    private lateinit var passwordInfo: TextView
    private lateinit var actionBar: androidx.appcompat.app.ActionBar
    private lateinit var window:Window


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        passwordGeneratorViewModel =
                ViewModelProvider(this).get(PasswordGeneratorViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_password_generator, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actionBar = (activity as AppCompatActivity?)!!.supportActionBar!!


        digitButton = view.findViewById(R.id.digits)
        letterButton = view.findViewById(R.id.letters)
        symbolButton = view.findViewById(R.id.symbols)
        generatedPassword = view.findViewById(R.id.generatedPassword)
        slider = view.findViewById(R.id.slider)
        screen = view.findViewById(R.id.password_generator_screen)
        passwordInfo = view.findViewById(R.id.password_strength_textview)

        digitButton.setOnClickListener(listener)
        letterButton.setOnClickListener(listener)
        symbolButton.setOnClickListener(listener)

        slider.addOnChangeListener(sliderListener)

        window= requireActivity().window


        updateColor(R.color.md_light_green_500)
        generatedPassword.text = passwordGeneratorViewModel.generatePassword()

        passwordGeneratorViewModel.checkedButtons.observe(viewLifecycleOwner, {
            Log.d(TAG, "onViewCreated: CheckedButtons observer called with value $it")
            updateSingleCheckedButton(it, digitButton.isChecked, letterButton.isChecked, symbolButton.isChecked)
        }
        )
        passwordGeneratorViewModel.list.observe(viewLifecycleOwner, {
//            Log.d(TAG, "onViewCreated: list observer called with value $it")
            generatedPassword.text = passwordGeneratorViewModel.generatePassword()
        }
        )
    }

    private fun updateSingleCheckedButton(checkedButtons: Int, isDigit: Boolean, isLetter: Boolean, isSymbol: Boolean) {
        if (checkedButtons >= 2 && passwordGeneratorViewModel.singleCheckedButton != null) {
            Log.d(TAG, "updateSingleCheckedButton: first called with checkedButtons: $checkedButtons")
            passwordGeneratorViewModel.singleCheckedButton!!.isClickable = true
            passwordGeneratorViewModel.singleCheckedButton = null
        } else if(checkedButtons<=1){
            Log.d(TAG, "updateSingleCheckedButton: second called with checkedButtons: $checkedButtons")
            passwordGeneratorViewModel.singleCheckedButton = when {
                isDigit -> digitButton
                isLetter -> letterButton
                else -> symbolButton
            }
            passwordGeneratorViewModel.singleCheckedButton!!.isClickable = false
        }
    }

    val listener = View.OnClickListener { view ->
        val id=passwordGeneratorViewModel.singleCheckedButton?.id
        id?.let { resources.getResourceName(it) }?.let { Log.d(TAG, it) }
        when (view.id) {
            R.id.digits -> {
                updateCheckedButtons(digitButton.isChecked)
                updateList(digitButton.isChecked, letterButton.isChecked, symbolButton.isChecked)
            }
            R.id.letters -> {
                updateCheckedButtons(letterButton.isChecked)
                updateList(digitButton.isChecked, letterButton.isChecked, symbolButton.isChecked)


            }
            R.id.symbols -> {
                updateCheckedButtons(symbolButton.isChecked)
                updateList(digitButton.isChecked, letterButton.isChecked, symbolButton.isChecked)

            }
            else -> {
                // do nothing
            }

        }
    }

    private fun updateList(isDigit: Boolean, isLetter: Boolean, isSymbol: Boolean) {
        passwordGeneratorViewModel.list.value = passwordGeneratorViewModel.generateList(isDigit, isLetter, isSymbol)
    }

    fun updateCheckedButtons(isChecked: Boolean) {
        if (isChecked)
            passwordGeneratorViewModel.checkedButtons.value = passwordGeneratorViewModel.checkedButtons.value?.plus(1)
        else
            passwordGeneratorViewModel.checkedButtons.value = passwordGeneratorViewModel.checkedButtons.value?.minus(1)
    }

    val sliderListener = Slider.OnChangeListener { slider, value, fromUser ->
//        Log.d(TAG, "called with value: $value")
        passwordGeneratorViewModel.passwordLength.value = value.toInt()
        generatedPassword.text = passwordGeneratorViewModel.generatePassword()

        if (value < 8) {
            updateColor(R.color.md_red_500)
            passwordInfo.text = "Not Safe"
        } else if (value < 16) {
            updateColor(R.color.md_light_green_500)
            passwordInfo.text = "Safe"

        } else {
            updateColor(R.color.md_green_500)
            passwordInfo.text = "Super Safe"

        }
    }

    private fun updateColor(colorId: Int) {
        val color = ContextCompat.getColor(requireContext(), colorId)
        val greyColor = ContextCompat.getColor(requireContext(), R.color.md_blue_grey_500)
        screen.setCardBackgroundColor(color)
        slider.thumbTintList = ColorStateList.valueOf(color)
        slider.trackActiveTintList = ColorStateList.valueOf(color)
        actionBar.setBackgroundDrawable(ColorDrawable(color))
        window.statusBarColor=color
    }


}