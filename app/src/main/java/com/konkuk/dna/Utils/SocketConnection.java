package com.konkuk.dna.Utils;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import okhttp3.OkHttpClient;


public class SocketConnection {
    private Socket mSocket;
    {
        try {
            //mSocket = IO.socket("https://dna.soyoungpark.me:9014");

//            OkHttpClient okHttpClient = new OkHttpClient.Builder().hostnameVerifier(myHostnameVerifier).sslSocketFactory(mySSLContext.getSocketFactory(), myX509TrustManaget).build();
//
//            IO.setDefaultOkHttpWebSocketFactory(okHttpClient);
//            IO.setDefaultOkHttpCallFactory(okHttpClient);
//
//            opts = new IO.Options();
//            opts.callFactory = okHttpClient;
//            opts.webSocketFactory = okHttpClient;

//            mSocket = IO.socket("https://13.125.78.77:9014", opts);
             mSocket = IO.socket("http://13.125.78.77:9014");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
