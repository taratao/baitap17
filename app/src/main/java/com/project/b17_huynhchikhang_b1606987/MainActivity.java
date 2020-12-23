package com.project.b17_huynhchikhang_b1606987;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;



public class MainActivity extends AppCompatActivity {

    private static final String URL = "http://192.168.1.189:3000";
    private Socket socket;
    {
        try {
            socket = IO.socket(URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        socket.open();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        socket.connect();
        socket.on("server-send-data", onRetrieveData);
        socket.emit("client-send-data", "Lap trinh anhd roid");
    }


    private Emitter.Listener onRetrieveData = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = (JSONObject)args[0];
                    try {
                        String content = jsonObject.getString("noidung");
                        Toast.makeText(MainActivity.this, "Noi dung: " + content, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };


}