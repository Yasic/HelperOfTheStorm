package com.application.yasic.crazyofthestorm.Util

import android.util.Log
import java.net.URL
import android.util.Base64

class WannaGet(val url: String){
    companion object{
        val mHtap = "aHR0cHM6Ly95YXNpY3l1LmNvbS8="
        fun htap(): String{
            return String(Base64.decode(mHtap, Base64.NO_WRAP))
        }
    }

    fun run():String{
        val response = URL(htap() + url).readText()
        Log.e(javaClass.simpleName, "WannaGet finish")
        return response
    }
}

