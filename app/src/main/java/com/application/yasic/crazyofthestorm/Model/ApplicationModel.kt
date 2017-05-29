package com.application.yasic.crazyofthestorm.Model

import android.app.Application

class ApplicationModel(): Application(){
    companion object {
        private var instance: Application? = null
        fun instance() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
