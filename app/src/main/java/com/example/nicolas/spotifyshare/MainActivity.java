package com.example.nicolas.spotifyshare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HomeFragment.HomeFragmentListener, WIFIDirectBroadcastReceiver.WIFIDirectBroadcastReceiverListener, GetUserFromPeerTask.GetUserFromPeerTaskListener {

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private BroadcastReceiver mReceiver;
    private IntentFilter mIntentFilter;
    private List<WifiP2pDevice> peers = new ArrayList();

    private ImageView profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpotemApplication.getInstance(this).loadUser(getApplicationContext());

        initToolbar();

        FragmentUtils.replaceFragment(this, R.id.fragment_container, new HomeFragment(), R.anim.none, R.anim.none);

        Intent serviceIntent = new Intent(this, WIFIDirectConnectionService.class);
        startService(serviceIntent);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);

        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(this.getClass().toString(), "discover peers success");
            }

            @Override
            public void onFailure(int reasonCode) {
                Log.d(this.getClass().toString(), "discover peers failure");
            }
        });
    }

    private void initToolbar() {
        profileButton = (ImageView) findViewById(R.id.profile_button);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver = new WIFIDirectBroadcastReceiver(mManager, mChannel, this);
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onHomeFragmentInteraction() {
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(this.getClass().toString(), "discover peers success");
            }

            @Override
            public void onFailure(int reasonCode) {
                Log.d(this.getClass().toString(), "discover peers failure");
            }
        });
    }

    @Override
    public void wifiEnabled(boolean enabled) {
        Log.d(this.getClass().toString(), "wifi enabled " + enabled);
    }

    @Override
    public void connectionsChanged() {
        Log.d(this.getClass().toString(), "connections changed");
    }

    @Override
    public void wifiStateChanged() {
        Log.d(this.getClass().toString(), "wifi state changed");
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
        peers.clear();
        peers.addAll(wifiP2pDeviceList.getDeviceList());
        if (peers.size() == 0) {
            Log.d(this.getClass().toString(), "No devices found");
            return;
        }
        Log.d(this.getClass().toString(), "peers available");
        connectToDevice();
    }

    public void connectToDevice() {
        // Picking the first device found on the network.
        WifiP2pDevice device = peers.get(0);

        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;

        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Log.d(MainActivity.this.getClass().toString(), "connect to device success");
            }

            @Override
            public void onFailure(int reason) {
                Log.d(MainActivity.this.getClass().toString(), "connect to device failure " + reason);
            }
        });
    }

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
        InetAddress inetAddress = wifiP2pInfo.groupOwnerAddress;
        GetUserFromPeerTask getUserFromPeerTask = new GetUserFromPeerTask(this, inetAddress);
        getUserFromPeerTask.executeInParallel();
    }

    @Override
    public void onGetUserFromPeerTaskSuccess(User user) {
        User foundUser = user;
        Log.d(this.getClass().toString(), "GetUserFromPeerTaskSuccess");
    }

    @Override
    public void onGetUserFromPeerTaskException(Exception e) {
        Log.d(this.getClass().toString(), "GetUserFromPeerTaskException");
    }
}
