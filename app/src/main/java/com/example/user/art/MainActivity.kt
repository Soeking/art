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

        paintView.client.connect()

        reset.setOnClickListener {
            paintView.clear()
        }

        eraser.setOnClickListener {
            when(paintView.change){
                true->{
                    paintView.change=false
                    eraser.setImageResource(R.drawable.ic_create_black_24dp)
                }
                false->{
                    paintView.change=true
                    eraser.setImageResource(R.drawable.ic_clear_black_24dp)
                }
            }
        }
    }
}