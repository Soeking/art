package com.example.user.art

import android.view.View
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

class WebSocket(activity: View,uri: URI): WebSocketClient(uri){
    val paintView=activity.findViewById<View>(R.id.view) as PaintView

    override fun onOpen(handshakedata: ServerHandshake?) {

    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {

    }

    override fun onMessage(othe: String?) {
        val x:Float
        val y:Float
        if (othe=="clear"){
            paintView.otherClear()
        }
        else{
            
        }
    }

    override fun onError(ex: Exception?) {

    }
}