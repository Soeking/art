package com.example.user.art

import android.util.Log
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

    override fun onMessage(message: String?) {
        Log.d("re",message)
        var at=""
        var bt=""
        var bo=false

        if (message=="clear") paintView.otherClear()
        else {
            for (i in message!!) {
                if (i == '/') {
                    bo = true
                    continue
                }
                if (bo) bt += i
                else at += i
            }
            paintView.otherDraw(at.toFloat(),bt.toFloat())
        }
    }

    override fun onError(ex: Exception?) {

    }
}