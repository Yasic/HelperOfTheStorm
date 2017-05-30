package com.application.yasic.crazyofthestorm.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.application.yasic.crazyofthestorm.Model.NetworkModel
import com.application.yasic.crazyofthestorm.R
import kotlinx.android.synthetic.main.activity_about.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AboutActivity(): AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        toolbar.title = "关于"
        setSupportActionBar(toolbar)
        val manager = this.packageManager
        val info = manager.getPackageInfo(this.packageName, 0)
        tv_application_version.text = "当前版本号：" + info.versionName
        doAsync {
            val text = NetworkModel().getAboutText()
            uiThread {
                tv_about.text = text
            }
        }
    }
}
