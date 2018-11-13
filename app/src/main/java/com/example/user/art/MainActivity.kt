package com.example.user.art

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val paintView = findViewById<View>(R.id.view) as PaintView

        reset.setOnClickListener {
            paintView.clear()
        }
    }
}