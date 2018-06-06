package com.dk.safehome1.provider;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.dk.safehome1.entity.Router;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dk on 6/6/18.
 */

public class RouterBuilder {

    private long enterId;
    private List<ScanResult> mScanResults;

    public RouterBuilder() {
    }

    public RouterBuilder(long enterId, List<ScanResult> scanResults) {
        this.enterId = enterId;
        mScanResults = scanResults;
    }

    public long getEnterId() {
        return enterId;
    }

    public void setEnterId(long enterId) {
        this.enterId = enterId;
    }

    public List<ScanResult> getScanResults() {
        return mScanResults;
    }

    public void setScanResults(List<ScanResult> scanResults) {
        mScanResults = scanResults;
    }

    public List<Router> build() {
        List<Router> routers = new ArrayList<>();

        for (ScanResult result : mScanResults) {
            Router router = new Router();
            router.setTitle(result.SSID);
            router.setSignalStrength(WifiManager.calculateSignalLevel(result.level, 100));
            router.setEnterId(enterId);

            routers.add(router);
        }

        Collections.sort(routers, (t1, t2) -> t2.getSignalStrength() - t1.getSignalStrength());

        return routers;
    }
}
