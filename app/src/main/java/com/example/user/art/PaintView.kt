package com.example.user.art

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.net.URI

class PaintView (context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint: Paint
    private val path: Path
    private val otherpaint:Paint
    private val otherpath:Path
    public val uri=URI("ws://192.168.0.22:8080/myws/echo")
    public val client=WebSocket(this,uri)

    init {
        path = Path()
        paint = Paint()
        paint.color =Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = 8f
    }

    init {
        otherpath = Path()
        otherpaint = Paint()
        otherpaint.color =Color.RED
        otherpaint.style = Paint.Style.STROKE
        otherpaint.strokeJoin = Paint.Join.ROUND
        otherpaint.strokeCap = Paint.Cap.ROUND
        otherpaint.strokeWidth = 8f
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
                Log.i("before","x:$x y:$y")
                client.send("$x/$y")
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
                Log.i("before","x:$x y:$y")
                client.send("$x/$y")
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                path.lineTo(x, y)
                client.send("$x/$y")
                invalidate()
            }
        }
        return true
    }

    fun otherDraw(x:Float,y:Float): Boolean {
        otherpath.moveTo(x,y)
        invalidate()
        return true
    }

    fun clear() {
        path.reset()
        client.send("clear")
        invalidate()
    }

    fun otherClear(){
        path.reset()
        invalidate()
    }
}
