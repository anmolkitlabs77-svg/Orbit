package com.orbit.other

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREF_NAME,
        Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun putString(key: String, value: String) {
        editor.putString(key, value).apply()
    }
    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }
    fun putBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun clearAll() {
        editor.clear().apply()
    }

    companion object {
        private const val PREF_NAME = "OrbitPreferences"
    }
}
