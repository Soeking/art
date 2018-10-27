package com.example.user.art

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class PaintView (context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint: Paint
    private val path: Path

    init {

        path = Path()
        paint = Paint()
        paint.color =Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = 10f
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                path.lineTo(x, y)
                invalidate()
            }
        }
        return true
    }

    fun clear() {
        path.reset()
        invalidate()
    }

    fun changeColor(){
        when(paint.color){
            Color.BLACK->paint.color=Color.RED
            Color.RED->paint.color=Color.BLUE
            Color.BLUE->paint.color=Color.BLACK
        }
    }
}
