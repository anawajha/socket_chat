package com.anawajha.babble.logic.socket

import android.app.Application
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket

class SocketCreate:Application() {
    private var mSocket :Socket? = IO.socket("http://3.3.3.11:5000")

    fun getSocket():Socket?{
        return mSocket
    }
}