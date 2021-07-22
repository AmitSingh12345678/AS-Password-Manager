package com.example.aspasswordmanager.ui.password_generator

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.switchmaterial.SwitchMaterial

class PasswordGeneratorViewModel : ViewModel() {
    private  val TAG = "PasswordGeneratorViewMo"
    val digits= listOf<Char>('0','1','2','3','4','5','6','7','8','9')
    val letters= listOf<Char>('a','b','c','d','e','f','g','h','i','j','k'
            ,'l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
            ,'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L'
            , 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    )
    val symbols= listOf<Char>('!','@','#','$','%','~','?')

    var checkedButtons: MutableLiveData<Int> = MutableLiveData(2)
    @SuppressLint("StaticFieldLeak")
    var singleCheckedButton: SwitchMaterial? = null
    var list: MutableLiveData<List<Any>> = MutableLiveData(getList(digits,letters))

    private fun getList(digits: List<Char>, letters: List<Char>): List<Any> {
         val list: MutableList<Any> = MutableList(0){}

        for(value in digits) list.add(value)
        for(value in letters) list.add(value)
        return list
    }

    var passwordLength: MutableLiveData<Int> = MutableLiveData(8)

    // TODO: 22-07-2021  change it's intialization 
    var password: MutableLiveData<String> = MutableLiveData(null)

    fun generateList(isDigit: Boolean, isLetter: Boolean, isSymbol: Boolean): List<Any>{
        val list: MutableList<Any> = MutableList(0){}

        if(isDigit){
            for(i in 1..5){
                for(value in digits) list.add(value)
            }
        }
        if(isLetter) {
            for(value in letters) list.add(value)
        }
        if(isSymbol){
            for(i in 1..7) {
                for(value in symbols) list.add(value)
            }
        }
        return list
    }
    fun generatePassword(): String{

        val len: Int? =passwordLength.value
        val tempPassword: CharArray= CharArray(len?:0)
        if (len != null) {
            for(i in (0..len-1)){
               val ind: Int= (0..(list.value?.size?.minus(1) ?: 0)).random()
                tempPassword[i]= (list.value?.get(ind?:0)) as Char
            }

        }

        return String(tempPassword)
    }
}