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