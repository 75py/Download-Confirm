package com.nagopy.android.downloadconfirm;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.Preference;
import android.util.AttributeSet;

public class VersionPreference extends Preference {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VersionPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setup(context);
    }

    public VersionPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context);
    }

    public VersionPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context);
    }

    public VersionPreference(Context context) {
        super(context);
        setup(context);
    }

    protected void setup(Context context){
        try {
            PackageInfo packageinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            setSummary(packageinfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e); // 通常起こりえない
        }
    }
}
