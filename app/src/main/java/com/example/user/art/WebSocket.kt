package com.example.user.art

import android.view.View
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI
import java.nio.ByteBuffer

class WebSocket(activity: View,uri: URI): WebSocketClient(uri){
    override fun onMessage(message: String?) {

    }

    val paintView=activity.findViewById<View>(R.id.view) as PaintView

    override fun onOpen(handshakedata: ServerHandshake?) {

    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {

    }

    override fun onMessage(othe: ByteBuffer?) {
        val x:Float
        val y:Float
        othe!!.position(0)
        x=othe.float
        if (x<0){
            paintView.otherClear()
        }
        else{
            othe.position(4)
            y=othe.float
            paintView.otherDraw(x,y)
        }
    }

    override fun onError(ex: Exception?) {

    }
}