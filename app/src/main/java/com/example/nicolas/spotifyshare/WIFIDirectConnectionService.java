package com.example.nicolas.spotifyshare;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Nicolas on 10/1/2016.
 */
public class WIFIDirectConnectionService extends IntentService {

    public WIFIDirectConnectionService() {
        super("WIFIDirectConnectionService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ServerSocket serverSocket;
        Socket client;

        while(true) {
            try {
                serverSocket = new ServerSocket(64222);
                serverSocket.toString();
                client = serverSocket.accept();

                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

//                String packageName = in.readLine().trim();
//                if (!packageName.equals(getPackageName())) {
//                    out.close();
//                    in.close();
//                    client.close();
//                    serverSocket.close();
//                    continue;
//                }

                User user = SpotemApplication.getInstance(this).getUser();
                User user2 = SpotemApplication.getInstance(this).loadUser(this);

                String jUser = new Gson().toJson(SpotemApplication.getInstance(this).getUser()) + "\n";
                out.write(jUser);

                out.close();
                in.close();
                client.close();
                serverSocket.close();

            } catch (IOException e) {
                Log.d(this.getClass().toString(), e.getMessage());
            }
        }
    }
}
