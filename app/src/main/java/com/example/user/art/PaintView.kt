package com.example.user.art

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.media.ImageReader
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

class PaintView (context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint: Paint
    private val path: Path
    private val otherpaint:Paint
    private val otherpath:Path
    lateinit var view:View
    private var backgroundHandler: Handler? = null
    private lateinit var file:File
    private var imageReader: ImageReader?=null
    private val onImageAvailableListener = ImageReader.OnImageAvailableListener {
        backgroundHandler?.post(ImageSaver(it.acquireNextImage(), file))
    }
    //lateinit var bitmap:Bitmap
    val uri=URI("ws://10.24.87.70:8080/myws/echo")
    val client=WebSocket(this,uri)
    val form= SimpleDateFormat("ssSSS")
    val data= Date(System.currentTimeMillis())
    val myid="${form.format(data)}"

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

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
                Log.i("before","x:$x y:$y")
                client.send("${myid}_a$x/$y")
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
                Log.i("before","x:$x y:$y")
                client.send("${myid}_$x/$y")
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                path.lineTo(x, y)
                client.send("${myid}_$x/$y")
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

    fun clear() {
        path.reset()
        otherpath.reset()
        client.send("clear")
        Log.i("clear","clear")
        invalidate()
    }

    fun otherClear(){
        path.reset()
        otherpath.reset()
        invalidate()
    }

    fun save(){
        val format= SimpleDateFormat("yyyyMMddHHmmss")
        val PIC_FILE_NAME = "${format.format(data)}.jpg"
        file = File("/storage/emulated/0/DCIM/Art/", PIC_FILE_NAME)
        imageReader = ImageReader.newInstance(view.width,view.height, ImageFormat.JPEG,2).apply {
            setOnImageAvailableListener(onImageAvailableListener, backgroundHandler)
        }
        val contentUri = Uri.fromFile(file)
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri)
        context.sendBroadcast(mediaScanIntent)
    }

    /**
    fun saveAsPngImage() {
        try {
            bitmap= Bitmap.createBitmap()
            val extStrageDir = Environment.getExternalStorageDirectory()
            val format= SimpleDateFormat("yyyyMMddHHmmss")
            val PIC_FILE_NAME = "${format.format(data)}.jpg"
            val file = File("/storage/emulated/0/DCIM/Art/", PIC_FILE_NAME)
            val outStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            outStream.close()

            Toast.makeText(
                context,
                "Image saved",
                Toast.LENGTH_SHORT).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    */
}
