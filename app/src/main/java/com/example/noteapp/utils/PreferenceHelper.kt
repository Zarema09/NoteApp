package com.example.noteapp.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(mainActivity: Context) {

    private lateinit var sharedPreferences: SharedPreferences

    init {
        unit(mainActivity)
    }

    fun unit(context: Context) {
        sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE)
    }

    var isOnBoardShown: Boolean
        get() = sharedPreferences.getBoolean("board",false)
        set(value) = sharedPreferences.edit().putBoolean("board",value).apply()

    var title: String?
        get() = sharedPreferences.getString("title","")
        set(value) = sharedPreferences.edit().putString("title",value)!!.apply()

    var isSignUpShown : Boolean
        get() =  sharedPreferences.getBoolean("sign",false)
        set(value) = sharedPreferences.edit().putBoolean("sign",value).apply()

}