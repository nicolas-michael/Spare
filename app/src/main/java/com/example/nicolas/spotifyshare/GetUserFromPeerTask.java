package com.example.nicolas.spotifyshare;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Nicolas on 10/2/2016.
 */
public class GetUserFromPeerTask extends ExceptionAsyncTask<String,Void, User> {

    private final GetUserFromPeerTaskListener getUserFromPeerTaskListener;
    private final InetAddress inetAddress;

    public GetUserFromPeerTask(GetUserFromPeerTaskListener getUserFromPeerTaskListener, InetAddress inetAddress) {
        this.getUserFromPeerTaskListener = getUserFromPeerTaskListener;
        this.inetAddress = inetAddress;
    }

    @Override
    protected User doInBackground() throws Exception {
        Socket client = new Socket(inetAddress, 64222);
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        //out.write(getUserFromPeerTaskListener.getApplicationContext().getPackageName());
        String jUser = in.readLine();
        Gson gson = new Gson();

        User user = gson.fromJson(jUser, User.class);
        return user;
    }

    @Override
    protected void onException(Exception e) {
        getUserFromPeerTaskListener.onGetUserFromPeerTaskException(e);
    }

    @Override
    protected void onSuccess(User user) {
        getUserFromPeerTaskListener.onGetUserFromPeerTaskSuccess(user);
    }

    public interface GetUserFromPeerTaskListener extends ContextImplementation {
        void onGetUserFromPeerTaskSuccess(User user);
        void onGetUserFromPeerTaskException(Exception e);
    }

    public interface ContextImplementation {
        Context getApplicationContext();
    }
}
