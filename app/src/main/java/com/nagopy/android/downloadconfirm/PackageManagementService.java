package com.nagopy.android.downloadconfirm;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;

/**
 * 一定時間後にクラスを有効にするためのサービス.
 */
public class PackageManagementService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String className = intent.getStringExtra("className");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    PackageManager packageManager = getPackageManager();
                    ComponentName componentName = new ComponentName(getPackageName(), className);
                    packageManager.setComponentEnabledSetting(
                            componentName,
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                stopSelf();
            }
        }, 1000);
        return super.onStartCommand(intent, flags, startId);
    }
}
