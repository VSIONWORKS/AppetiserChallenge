package com.exam.appetiserchallenge.utils

import android.content.Context
import android.content.SharedPreferences
import com.exam.appetiserchallenge.utils.Constants.PREFS_NAME

class SharedPrefs(context: Context) {

    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, value: Any) {
        prefs.set(KEY_NAME, value)
    }

}