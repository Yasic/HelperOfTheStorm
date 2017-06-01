package com.application.yasic.crazyofthestorm.Fragment

import android.app.Dialog
import android.app.DownloadManager
import android.app.ProgressDialog
import android.content.*
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.IBinder
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import com.application.yasic.crazyofthestorm.Activity.AboutActivity
import com.application.yasic.crazyofthestorm.Activity.FeedBackActivity
import com.application.yasic.crazyofthestorm.Activity.HeroDisplayActivity
import com.application.yasic.crazyofthestorm.Model.NetworkModel
import com.application.yasic.crazyofthestorm.R
import com.application.yasic.crazyofthestorm.Service.DownloadService
import com.application.yasic.crazyofthestorm.Util.MoreListAdapter
import com.application.yasic.crazyofthestorm.Util.WannaGet
import kotlinx.android.synthetic.main.fragment_more.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.io.File


class MoreListFragment() : Fragment() {
    private var binder:DownloadService.DownloadBinder? = null
    private var downloading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_more_list.layoutManager = LinearLayoutManager(activity)
        val titleList = arrayOf(
                activity.resources.getString(R.string.image_enable_switch),
                activity.resources.getString(R.string.feedback_activity_title),
                activity.resources.getString(R.string.about_activity_title),
                activity.resources.getString(R.string.update_check_title)
        )
        rv_more_list.adapter = MoreListAdapter(titleList, object : MoreListAdapter.OnItemClickListener {
            override fun invoke(title: String) {
                when (title) {
                    activity.resources.getString(R.string.feedback_activity_title)-> {
                        val intent = Intent(activity, FeedBackActivity::class.java)
                        startActivity(intent)
                    }
                    activity.resources.getString(R.string.about_activity_title) -> {
                        val intent = Intent(activity, AboutActivity::class.java)
                        startActivity(intent)
                    }
                    activity.resources.getString(R.string.update_check_title) -> {
                        updateApplication()
                    }
                }
            }
        })
    }

    private fun updateApplication(){
        if (!downloading){
            val dialog = showCheckVersionDialog()
            doAsync {
                val needUpdate = NetworkModel().checkApplicationVersion()
                uiThread {
                    dialog.dismiss()
                    if (needUpdate){
                        activity.toast("即将下载新版本..")
                        val intent = Intent(activity, DownloadService::class.java)
                        val bundle = Bundle()
                        bundle.putString("url", WannaGet.htap() + "heroapp/apkfile")
                        bundle.putString("name", "HelperOfTheStorm.apk")
                        intent.putExtras(bundle)
                        activity.startService(intent)
                        downloading = true
                        val intentFilter = IntentFilter()
                        intentFilter.addAction("DOWNLOADFINISH")
                        activity.registerReceiver(receiver, intentFilter)
                    }else{
                        activity.toast("当前已是最新版本")
                    }
                }
            }
        }else{
            showCancelDialog()
        }
    }

    private fun showCheckVersionDialog(): ProgressDialog{
        val dialog = ProgressDialog(activity)
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        dialog.setMessage("正在检查版本...")
        dialog.isIndeterminate = true
        dialog.setCancelable(false)
        dialog.show()
        return dialog
    }

    private fun showCancelDialog(): AlertDialog{
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setMessage("正在下载，是否取消")
        dialogBuilder.setPositiveButton("是", object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface, p1: Int) {
                val intent = Intent(activity, DownloadService::class.java)
                activity.stopService(intent)
                downloading = false
                p0.dismiss()
            }
        })
        dialogBuilder.setNegativeButton("否", object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface, p1: Int) {
                p0.dismiss()
            }

        })
        val dialog = dialogBuilder.create()
        dialog.show()
        return dialog
    }

    private val connection = object : ServiceConnection{
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            binder = p1 as DownloadService.DownloadBinder
        }

        override fun onServiceDisconnected(p0: ComponentName?) {

        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            downloading = false
        }
    }

    override fun onDestroy() {
        activity.unregisterReceiver(receiver)
        super.onDestroy()
    }
}


