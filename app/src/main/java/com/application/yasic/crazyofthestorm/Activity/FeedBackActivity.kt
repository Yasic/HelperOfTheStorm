package com.application.yasic.crazyofthestorm.Activity

import com.application.yasic.crazyofthestorm.R
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.application.yasic.crazyofthestorm.Model.NetworkModel
import kotlinx.android.synthetic.main.activity_feedback.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class FeedBackActivity(): AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        toolbar.title = "反馈"
        setSupportActionBar(toolbar)

        val manager = this.packageManager
        val info = manager.getPackageInfo(this.packageName, 0)
        tv_application_version.text = "当前版本号：" + info.versionName
        tv_android_version.text = "Android版本：" + android.os.Build.VERSION.RELEASE
        tv_os_title.text = "手机型号：" + android.os.Build.MODEL
        view_loading.visibility = View.GONE

        btn_submit.setOnClickListener {
            if (et_feedback_text.text.toString() == ""){
                toast("貌似忘记填反馈信息了")
            } else {
                btn_submit.isClickable = false
                view_loading.visibility = View.VISIBLE
                scrollView.visibility = View.GONE
                doAsync {
                    val response = NetworkModel().postFeedback(this@FeedBackActivity, tv_application_version.text.toString(), tv_os_title.text.toString(),
                            tv_android_version.text.toString(), et_feedback_text.text.toString(), et_connect_function.text.toString())
                    uiThread {
                        toast(response)
                        if(response == "提交成功"){
                            et_feedback_text.setText("")
                            et_connect_function.setText("")
                        }
                        view_loading.visibility = View.GONE
                        scrollView.visibility = View.VISIBLE
                        btn_submit.isClickable = true
                    }
                }
            }
        }
    }
}
