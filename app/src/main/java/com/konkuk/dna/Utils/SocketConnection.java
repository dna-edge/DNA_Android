package com.konkuk.dna.Utils;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class SocketConnection {
    private Socket mSocket;
    {
        try {
            //mSocket = IO.socket("https://dna.soyoungpark.me:9014");
            mSocket = IO.socket("https://13.125.78.77:9014");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
