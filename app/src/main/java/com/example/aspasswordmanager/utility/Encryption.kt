package com.example.aspasswordmanager.utility

import android.content.ClipData
import android.content.Context
import android.util.Log
import com.example.aspasswordmanager.ui.home.database.PasswordEntity
import kotlin.properties.Delegates

public class Encryption() {
    companion object {
        private  val TAG = "Encryption"
        private val chars = listOf<Char>(
            '0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            'a',
            'b',
            'c',
            'd',
            'e',
            'f',
            'g',
            'h',
            'i',
            'j',
            'k',
            'l',
            'm',
            'n',
            'o',
            'p',
            'q',
            'r',
            's',
            't',
            'u',
            'v',
            'w',
            'x',
            'y',
            'z',
            'A',
            'B',
            'C',
            'D',
            'E',
            'F',
            'G',
            'H',
            'I',
            'J',
            'K',
            'L',
            'M',
            'N',
            'O',
            'P',
            'Q',
            'R',
            'S',
            'T',
            'U',
            'V',
            'W',
            'X',
            'Y',
            'Z'
        )

        fun encrypt(text: String,KEY: Int): String {
            if(text.isEmpty()) return ""
            val len = text.length
            val paddingStartLength: Int = KEY % len
            val paddingEndLength: Int = len - paddingStartLength
            Log.d(TAG, "encrypt: $paddingStartLength")
            val paddedString: String =
                randomStringGenerator(paddingStartLength) + leftShift(text,KEY) + randomStringGenerator(
                    paddingEndLength
                )
            Log.d(TAG, "encrypt: $paddedString")
            val temp=paddedString.toCharArray()
            for(i in temp.indices){
                temp[i]=(temp[i]+KEY)
            }
            return String(temp)
        }
        fun encrypt(item: PasswordEntity, KEY: Int): PasswordEntity {
            val eItem: PasswordEntity = PasswordEntity(
                Encryption.encrypt(item.title,KEY),
                Encryption.encrypt(item.username,KEY),
                Encryption.encrypt(item.password,KEY),
                Encryption.encrypt(item.website,KEY),
                Encryption.encrypt(item.note,KEY)
            )
            eItem.id=item.id
            return eItem
        }

        fun decrypt(text: String,KEY: Int): String {
            if(text.isEmpty()) return ""
            val len=text.length/2
            val paddingStartLength: Int = KEY % len
            var oriString=text.substring(paddingStartLength,paddingStartLength+len)
            Log.d(TAG, "decrypt: $oriString")
            oriString= rightShift(oriString,KEY)
            Log.d(TAG, "decrypt: $oriString")


            val temp=oriString.toCharArray()
            for(i in temp.indices){
                temp[i]=(temp[i]-KEY)
            }
            return String(temp)
        }
        fun decrypt(eitem: PasswordEntity, KEY: Int): PasswordEntity {
            val dItem: PasswordEntity = PasswordEntity(
                Encryption.decrypt(eitem.title,KEY),
                Encryption.decrypt(eitem.username,KEY),
                Encryption.decrypt(eitem.password,KEY),
                Encryption.decrypt(eitem.website,KEY),
                Encryption.decrypt(eitem.note,KEY)
            )
            dItem.id=eitem.id
            return dItem
        }

        private fun randomStringGenerator(len: Int): String {
            var s: String = ""
            for (i in 1..len) {
                val c: Char = chars[(chars.indices).random()]
                s += c;
            }
            return s;

        }

        private fun rightShift(text: String,KEY: Int): String {
            val len = text.length;
            val k = KEY % len
            return text.substring(len - k) + text.substring(0, len - k)
        }

        private fun leftShift(text: String,KEY: Int): String {
            val len = text.length
            val k = KEY % len
            return text.substring(k) + text.substring(0,k)
        }
    }


}