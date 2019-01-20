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
    val uri=URI("ws://192.168.0.22:8080/myws/echo")
    val client=WebSocket(this,uri)
    val form= SimpleDateFormat("ssSSS")
    val data= Date(System.currentTimeMillis())
    val myid="${form.format(data)}"
    var lastX=0f
    var lastY=0f
    var otherX=0f
    var otherY=0f

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
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, paint)
        canvas.drawPath(otherpath,otherpaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        val xx=x/width
        val yy=y/height

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
                Log.i("before","x:$xx y:$yy")
                client.send("${myid}_a$xx/$yy")
                lastX=x
                lastY=y
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
                Log.i("before","x:$xx y:$yy")
                client.send("${myid}_$xx/$yy")
                lastX=x
                lastY=y
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                path.lineTo(x, y)
                client.send("${myid}_$xx/$yy")
                lastX=x
                lastY=y
                invalidate()
            }
        }
        return true
    }

    fun otherMove(xx: Float,yy: Float):Boolean{
        val x=xx*width
        val y=yy*height
        otherpath.moveTo(x,y)
        otherX=x
        otherY=y
        invalidate()
        Log.i("otherMove","success")
        return true
    }

    fun otherDraw(xx:Float,yy:Float): Boolean {
        val x=xx*width
        val y=yy*height
        otherpath.lineTo(x,y)
        otherX=x
        otherY=y
        invalidate()
        Log.i("otherDraw","success")
        return true
    }

    fun clear() {
        client.send("clear")
        Log.i("clear","clear")
        invalidate()
    }

    fun otherClear(){
        path.reset()
        path.moveTo(lastX,lastY)
        otherpath.reset()
        otherpath.moveTo(otherX,otherY)
        invalidate()
    }

    fun myClear(){
        path.reset()
        client.send("${myid}clear")
        invalidate()
    }

    fun partClear(){
        otherpath.reset()
        invalidate()
    }
}
