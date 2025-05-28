package com.example.lab5

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class LevelView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF333333.toInt()
        strokeWidth = 8f
    }

    private var angleDeg: Float = 0f

    fun setAngle(deg: Float) {
        angleDeg = deg
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cx = width / 2f
        val cy = height / 2f

        canvas.save()
        canvas.translate(cx, cy)
        canvas.rotate(angleDeg)

        canvas.drawLine(-cx, 0f, cx, 0f, paint)

        canvas.restore()
    }
}
