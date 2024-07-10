package com.example.data.cache

import android.content.Context
import javax.inject.Inject

private const val FROM_INPUT_PREFERENCES = "FROM_INPUT_PREFERENCES"
private const val FROM_INPUT_TEXT = "FROM_INPUT_TEXT"

class SharedPreferencesStorage @Inject constructor(
    private val context: Context
) {

    fun saveStringToSharedPrefs(text: String) {
        val sharedPreferences =
            context.getSharedPreferences(FROM_INPUT_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(FROM_INPUT_TEXT, text)
        editor.apply()
    }

    fun getStringFromSharedPrefs(): String {
        val sharedPreferences =
            context.getSharedPreferences(FROM_INPUT_PREFERENCES, Context.MODE_PRIVATE)
        return sharedPreferences?.getString(FROM_INPUT_TEXT, "") ?: ""
    }
}