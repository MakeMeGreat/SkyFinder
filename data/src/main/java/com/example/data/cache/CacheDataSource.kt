package com.example.data.cache

import javax.inject.Inject

class CacheDataSource @Inject constructor(
    private val sharedPreferencesStorage: SharedPreferencesStorage
) {
    fun saveText(text: String) = sharedPreferencesStorage.saveStringToSharedPrefs(text)

    fun getText(): String = sharedPreferencesStorage.getStringFromSharedPrefs()
}