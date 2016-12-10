package com.example.nicolas.spotifyshare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;

/**
 * Created by Nicolas on 10/1/2016.
 */
public class WIFIDirectBroadcastReceiver extends BroadcastReceiver {

    private WIFIDirectBroadcastReceiverListener wifiDirectBroadcastReceiverListener;
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;

    public WIFIDirectBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                                       WIFIDirectBroadcastReceiverListener wifiDirectBroadcastReceiverListener) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.wifiDirectBroadcastReceiverListener = wifiDirectBroadcastReceiverListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                wifiDirectBroadcastReceiverListener.wifiEnabled(true);
            } else {
                wifiDirectBroadcastReceiverListener.wifiEnabled(false);
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            if (mManager != null) {
                mManager.requestPeers(mChannel, wifiDirectBroadcastReceiverListener);
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            wifiDirectBroadcastReceiverListener.connectionsChanged();
            if (mManager == null) {
                return;
            }
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
            //if (networkInfo.isConnected()) {
                mManager.requestConnectionInfo(mChannel, wifiDirectBroadcastReceiverListener);
           // }
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            wifiDirectBroadcastReceiverListener.wifiStateChanged();
        }
    }

    public interface WIFIDirectBroadcastReceiverListener extends WifiP2pManager.PeerListListener, WifiP2pManager.ConnectionInfoListener {
        void wifiEnabled(boolean enabled);
        void connectionsChanged();
        void wifiStateChanged();
    }
}
