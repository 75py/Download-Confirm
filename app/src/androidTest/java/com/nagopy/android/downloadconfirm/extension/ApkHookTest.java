package com.nagopy.android.downloadconfirm.extension;

import android.support.test.runner.AndroidJUnit4;

import com.nagopy.android.downloadconfirm.R;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ApkHookTest extends HookTest {


    @Test
    public void testApkHookEnabled() throws Throwable {
        testCheckboxOn("apk", R.string.label_apk);
    }

    @Test
    public void testApkHookDisabled() throws Throwable {
        testCheckboxOff("apk");
    }
}