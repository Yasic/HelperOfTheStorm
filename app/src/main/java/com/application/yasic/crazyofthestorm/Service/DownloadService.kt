package com.application.yasic.crazyofthestorm.Service

import android.app.*
import android.content.*
import android.net.Uri
import android.os.Binder
import android.os.Environment
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import com.application.yasic.crazyofthestorm.Activity.MainActivity
import com.application.yasic.crazyofthestorm.Model.ApplicationModel
import com.application.yasic.crazyofthestorm.R
import com.application.yasic.crazyofthestorm.Util.WannaGet
import okhttp3.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.Future

class DownloadService() : Service() {
    private val mBinder = DownloadBinder()
    private val NOTIFICATIONID = 1235
    private var progress:Int = 0
    private var mNotificationManager: NotificationManager? = null
    private var asyncTask: Future<Unit>? = null
    private var call: Call? = null

    override fun onCreate() {
        Log.e("oncreate", "1")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.e("start", "1")
        if (intent == null){
            Log.e("intent", "null")
            return START_NOT_STICKY
        }else {
            val fileUrl = intent.getStringExtra("url")
            var fileName = intent.getStringExtra("name")
            val okHttpClient = OkHttpClient()
            val request = Request.Builder().url(fileUrl).build()

            mNotificationManager = getSystemService(android.content.Context.NOTIFICATION_SERVICE) as NotificationManager
            val mRemoteViews = RemoteViews(packageName, R.layout.notification_download_service)
            val notificationIntent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
            val notification = Notification.Builder(this).setContent(mRemoteViews).setAutoCancel(true).setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.main_icon)
                    .setWhen(System.currentTimeMillis())
                    .build()
            notification.flags = Notification.FLAG_ONGOING_EVENT
            startForeground(NOTIFICATIONID, notification)
            notification.contentView.setProgressBar(R.id.pb_download, 100, 0, false)
            progress = 0
            notification.contentView.setTextViewText(R.id.tv_progress, "0%")
            mNotificationManager!!.notify(NOTIFICATIONID, notification)

            asyncTask = doAsync {
                call = okHttpClient.newCall(request)
                call!!.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        uiThread {
                            ApplicationModel.instance().toast("下载出错,已停止")
                            stopForeground(true)
                            onDestroy()
                        }
                    }
                    override fun onResponse(call: Call, response: Response) {
                        var inputStream: InputStream? = null
                        val buf = ByteArray(2048)
                        val total = response.body()?.contentLength()
                        var current: Long = 0
                        var len: Int = 0
                        inputStream = response.body()?.byteStream()
                        var file = File("")
                        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                            val sdCardDir = Environment.getExternalStorageDirectory()
                            file = File(sdCardDir, "Download/" + fileName)
                        }
                        val fileOutputStream = FileOutputStream(file.toString(), false)
                        len = inputStream?.read(buf)!!
                        while (len != -1) {
                            current += len
                            uiThread {
                                progress = Math.floor((current.toDouble() / total!!.toDouble()) * 100).toInt()
                                mRemoteViews.setProgressBar(R.id.pb_download, 100, progress, false)
                                mRemoteViews.setTextViewText(R.id.tv_progress, progress.toString() + "%")
                                notification.contentView = mRemoteViews
                                mNotificationManager!!.notify(NOTIFICATIONID, notification)
                            }
                            fileOutputStream.write(buf, 0, len)
                            len = inputStream?.read(buf)!!
                        }
                        fileOutputStream.flush()
                        installAPK(file)
                        onDestroy()
                    }
                })
            }
            return START_NOT_STICKY
        }
    }

    private fun installAPK(file: File) {
        if (!file.exists()) return
        val intent = Intent(Intent.ACTION_VIEW)
        val uri = Uri.parse("file://" + file.toString())
        intent.setDataAndType(uri, "application/vnd.android.package-archive")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        this.startActivity(intent)
    }

    override fun onBind(p0: Intent?): IBinder {
        return mBinder
    }

    inner class DownloadBinder: Binder(){
        fun getProgress(): Int{
            return progress
        }

        fun finish(){
            onDestroy()
        }
    }

    override fun onDestroy() {
        Log.e("destroyService", "1")
        mNotificationManager!!.cancelAll()
        val intent = Intent("DOWNLOADFINISH")
        sendBroadcast(intent)
        stopForeground(true)
        call!!.cancel()
        asyncTask!!.cancel(true)
        stopSelf()
        super.onDestroy()
    }
}
