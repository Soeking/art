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
        Log.i("open","could open")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {

    }

    override fun onMessage(message: String?) {
        Log.i("re","after:$message")
        var at=""
        var bt=""
        var bo=false
        var und=false

        if (message=="clear") paintView.otherClear()
        else {
            var co=0
            var id=""
            for (t in message!!){
                id+=t
                co++
                if (co==5) break
            }
            Log.i("id",id)
            if (id!=paintView.myid) {
                if (message!![6] == 'a') {
                    for (i in message!!) {
                        if (i == '/') {
                            bo = true
                            continue
                        }
                        if (i =='_'){
                            und=true
                            continue
                        }
                        if (i == 'a') continue
                        if (und) {
                            if (bo) bt += i
                            else at += i
                        }
                    }
                    paintView.otherMove(at.toFloat(), bt.toFloat())
                } else {
                    for (i in message!!) {
                        if (i == '/') {
                            bo = true
                            continue
                        }
                        if (i =='_'){
                            und=true
                            continue
                        }
                        if (und) {
                            if (bo) bt += i
                            else at += i
                        }
                    }
                    paintView.otherDraw(at.toFloat(), bt.toFloat())
                }
            }
        }
    }

    override fun onError(ex: Exception?) {
        Log.i("err","happen error")
    }
}