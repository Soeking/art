package com.example.user.art

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.ImageFormat
import android.media.ImageReader
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(){
    /**
    private var backgroundHandler: Handler? = null
    private lateinit var file:File
    private var imageReader:ImageReader?=null
    private val onImageAvailableListener = ImageReader.OnImageAvailableListener {
        backgroundHandler?.post(ImageSaver(it.acquireNextImage(), file))
    }
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)

        val paintView = findViewById<View>(R.id.view) as PaintView

        paintView.client.connect()

        reset.setOnClickListener {
            paintView.clear()
        }

        save.setOnClickListener {
            paintView.save()
            /**
            val form= SimpleDateFormat("yyyyMMddHHmmss")
            val data= Date(System.currentTimeMillis())
            val PIC_FILE_NAME = "${form.format(data)}.jpg"
            file = File("/storage/emulated/0/DCIM/Art/", PIC_FILE_NAME)
            val contentUri = Uri.fromFile(file)
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri)
            //context.sendBroadcast(mediaScanIntent)
            */
        }
    }
/**
    override fun onResume() {
        super.onResume()
        imageReader = ImageReader.newInstance(1080,1920,
            ImageFormat.JPEG,2).apply {
            setOnImageAvailableListener(onImageAvailableListener, backgroundHandler)
        }
    }
    */
}