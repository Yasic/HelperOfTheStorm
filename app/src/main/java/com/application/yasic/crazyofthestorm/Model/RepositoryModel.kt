package com.application.yasic.crazyofthestorm.Model

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class RepositoryModel(){
    private val imageSharedPreference: SharedPreferences
    private val context: Context
    init {
        context = ApplicationModel.instance().applicationContext
        imageSharedPreference = context.getSharedPreferences("ImageSwitch", Activity.MODE_PRIVATE)
    }

    fun isImageEnable(): Boolean{
        return imageSharedPreference.getBoolean("enable", true)
    }

    fun setImageEnable(isEnabled: Boolean){
        imageSharedPreference.edit().putBoolean("enable", isEnabled).apply()
    }
}
