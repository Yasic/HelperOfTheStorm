package com.application.yasic.crazyofthestorm.View

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.widget.RelativeLayout
import com.application.yasic.crazyofthestorm.R
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class LoadingView : RelativeLayout {
    private var paint: Paint
    private val balls: MutableList<Ball> = mutableListOf()
    private var runningFlag = true
    private val radius = 50
    private var textTimerOn = false
    private var textDrawEnable = false

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
    }

    init {
        paint = Paint()
        paint.isAntiAlias = false
        paint.style = Paint.Style.FILL
        this.setBackgroundColor(context.resources.getColor(R.color.colorPrimaryDark))
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        val alphas = arrayOf("ff", "cc", "99", "66", "33")
        val colors = arrayOf("f7f8f3", "3399ff", "4bae4f")
        if (balls.size == 0) {
            for (group in 0..2) {
                for (item in 0..3) {
                    var ball = Ball()
                    ball.startDelta = 0.0F + group * 120.0f
                    ball.endDelta = 120.0F + group * 120.0f
                    ball.vDelta = (4 - 0.7 * item).toFloat()
                    ball.setColor("#" + alphas[item] + colors[group])
                    balls.add(ball)
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(!textTimerOn){
            startTextDrawing(canvas)
            textTimerOn = true
        }

        if (textDrawEnable){
            val textPaint = Paint()
            textPaint.isAntiAlias = false
            textPaint.style = Paint.Style.FILL
            textPaint.color = Color.parseColor(context.resources.getString(R.color.lightPrimaryColor))
            textPaint.textAlign = Paint.Align.CENTER
            textPaint.textSize = 48F
            canvas.drawText("加载缓慢，请检查网络", (width/2).toFloat(), (height/2 + 3 * radius).toFloat(), textPaint)
        }

        if (runningFlag) {
            var singleRunning = false
            balls.forEach {
                if (it.endDelta > it.startDelta) {
                    singleRunning = true
                }else{
                    it.startDelta = it.endDelta
                }
            }
            runningFlag = singleRunning
            if (!runningFlag) {
                class updateEndDelta() : TimerTask() {
                    override fun run() {
                        balls.forEach() {
                            it.endDelta += 120
                            runningFlag = true
                        }
                    }
                }
                Timer().schedule(updateEndDelta(), 300)
            }
        }

        canvas.save()
        paint.style = Paint.Style.STROKE
        paint.color = Color.parseColor(context.resources.getString(R.color.lightPrimaryColor))
        canvas.drawCircle((width/2).toFloat(), (height/2).toFloat(), radius.toFloat(), paint)
        paint.style = Paint.Style.FILL
        canvas.restore()

        balls.forEachIndexed {
            index, it ->
            run {
                canvas.save()
                paint.color = it.getColor()
                canvas.rotate(it.startDelta, (width / 2).toFloat(), (height / 2).toFloat())
                canvas.drawCircle((width / 2).toFloat(), (height / 2 - radius).toFloat(), 10.0f, paint)
                if (it.endDelta > it.startDelta) {
                    it.startDelta += it.vDelta
                }
                canvas.restore()
            }
        }
        invalidate()
    }

    private fun startTextDrawing(canvas: Canvas){
        class ShowNetWorkTips():TimerTask(){
            override fun run() {
                textDrawEnable = true
            }
        }
        Timer().schedule(ShowNetWorkTips(), 12000)
    }

    private class Ball() {
        var startDelta = 0.0f
        var endDelta = 0.0f
        var vDelta = 0.0f
        private var color = Color.parseColor("#000000")

        fun setColor(color: String): Ball {
            this.color = Color.parseColor(color)
            return this
        }

        fun getColor(): Int {
            return color
        }

    }

}
