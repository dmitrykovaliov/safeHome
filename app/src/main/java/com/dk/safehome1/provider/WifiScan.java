package com.dk.safehome1.provider;

import android.app.Application;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.List;

/**
 * Created by dk on 6/4/18.
 */

public class WifiScan {

    public static List<ScanResult> scan (Application context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

      wifiManager.startScan();

       return wifiManager.getScanResults();
    }

}
