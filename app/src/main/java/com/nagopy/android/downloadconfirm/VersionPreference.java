package com.nagopy.android.downloadconfirm;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.preference.Preference;
import android.util.AttributeSet;

/**
 * バージョン名を表示するPreferenceクラス.
 */
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

    protected void setup(Context context) {
        setSummary(BuildConfig.VERSION_NAME);
    }
}
