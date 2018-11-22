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
import java.text.SimpleDateFormat
import java.util.*

class PaintView (context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint: Paint
    private val path: Path
    private val otherpaint:Paint
    private val otherpath:Path
    private val whitepaint:Paint
    private val whitepath:Path
    val uri=URI("ws://192.168.0.22:8080/myws/echo")
    val client=WebSocket(this,uri)
    val form= SimpleDateFormat("ssSSS")
    val data= Date(System.currentTimeMillis())
    val myid="${form.format(data)}"
    var change=true

    init {
        path = Path()
        paint = Paint()
        paint.color =Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = 8f

        otherpath = Path()
        otherpaint = Paint()
        otherpaint.color =Color.RED
        otherpaint.style = Paint.Style.STROKE
        otherpaint.strokeJoin = Paint.Join.ROUND
        otherpaint.strokeCap = Paint.Cap.ROUND
        otherpaint.strokeWidth = 8f

        whitepath = Path()
        whitepaint = Paint()
        whitepaint.color =Color.WHITE
        whitepaint.style = Paint.Style.STROKE
        whitepaint.strokeJoin = Paint.Join.ROUND
        whitepaint.strokeCap = Paint.Cap.ROUND
        whitepaint.strokeWidth = 28f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, paint)
        canvas.drawPath(otherpath,otherpaint)
        canvas.drawPath(whitepath,whitepaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (change) {
                    path.moveTo(x, y)
                    Log.i("before", "x:$x y:$y")
                    client.send("${myid}_a$x/$y")
                }
                else{
                    client.send("wa$x/$y")
                }
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                if (change) {
                    path.lineTo(x, y)
                    Log.i("before", "x:$x y:$y")
                    client.send("${myid}_$x/$y")
                }
                else{
                    client.send("w$x/$y")
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                if (change) {
                    path.lineTo(x, y)
                    Log.i("before", "x:$x y:$y")
                    client.send("${myid}_$x/$y")
                }
                else{
                    client.send("w$x/$y")
                }
                invalidate()
            }
        }
        return true
    }

    fun otherMove(x: Float,y: Float):Boolean{
        otherpath.moveTo(x,y)
        invalidate()
        Log.i("otherMove","success")
        return true
    }

    fun otherDraw(x:Float,y:Float): Boolean {
        otherpath.lineTo(x,y)
        invalidate()
        Log.i("otherDraw","success")
        return true
    }

    fun whiteMove(x:Float,y:Float):Boolean{
        whitepath.moveTo(x,y)
        invalidate()
        return true
    }

    fun whiteDraw(x:Float,y: Float):Boolean{
        whitepath.lineTo(x,y)
        invalidate()
        return true
    }

    fun clear() {
        client.send("clear")
        Log.i("clear","clear")
        invalidate()
    }

    fun otherClear(){
        path.reset()
        otherpath.reset()
        whitepath.reset()
        invalidate()
    }
}
