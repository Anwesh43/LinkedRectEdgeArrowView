package com.anwesh.uiprojects.rectedgearrowview

/**
 * Created by anweshmishra on 15/10/20.
 */

import android.view.View
import android.view.MotionEvent
import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.graphics.Color
import android.graphics.Path
import android.graphics.Canvas
import android.graphics.RectF


val colors : Array<Int> = arrayOf(
        "#F44336",
        "#4CAF50",
        "#2196F3",
        "#FF9800",
        "#673AB7"
).map {
    Color.parseColor(it)
}.toTypedArray()
val parts : Int = 4
val scGap : Float = 0.02f / parts
val strokeFactor : Float = 90f
val barWFactor : Float = 6.9f
val barHFactor : Float = 12.3f
val delay : Long = 20
val backColor : Int = Color.parseColor("#BDBDBD")

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawRectArrowEdge(scale : Float, w : Float, h : Float, paint : Paint) {
    val hBar : Float = h / barHFactor
    val wBar : Float = w / barWFactor
    val sf : Float = scale.sinify()
    val sf1 : Float = sf.divideScale(0, parts)
    val sf2 : Float = sf.divideScale(1, parts)
    val sf3 : Float = sf.divideScale(2, parts)
    save()
    translate(w / 2, h / 2)
    for (j in 0..1) {
        save()
        scale(1f - 2 * j, 1f)
        for (k in 0..1) {
            save()
            translate(0f, -hBar / 2 + hBar * j)
            drawLine(0f, 0f, wBar * 0.5f * sf1, 0f, paint)
            drawLine(wBar * 0.5f, 0f, wBar * 0.5f + hBar * 0.5f * sf2, (1f - 2 * k) * hBar * 0.5f * sf2, paint)
            restore()
        }
        save()
        val path : Path = Path()
        path.moveTo(0f, -hBar / 2)
        path.lineTo(wBar / 2, -hBar / 2)
        path.lineTo(wBar / 2 + hBar / 2, 0f)
        path.lineTo(wBar / 2, hBar / 2)
        path.lineTo(0f, hBar / 2)
        path.lineTo(0f, -hBar / 2)
        clipPath(path)
        drawRect(RectF(0f, -hBar / 2, (wBar + hBar / 2) * sf3, hBar / 2), paint)
        restore()
        restore()
    }
    restore()
}

fun Canvas.drawRAENode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawRectArrowEdge(scale, w, h, paint)
}

class RectArrowEdgeView(ctx : Context) : View(ctx) {

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            scale += scGap * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }
}