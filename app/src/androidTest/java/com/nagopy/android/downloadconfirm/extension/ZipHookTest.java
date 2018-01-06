package com.nagopy.android.downloadconfirm.extension;

import android.support.test.runner.AndroidJUnit4;

import com.nagopy.android.downloadconfirm.R;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ZipHookTest extends HookTest {


    @Test
    public void testZipHookEnabled() throws Throwable {
        testCheckboxOn("zip", R.string.label_zip);
    }

    @Test
    public void testZipHookDisabled() throws Throwable {
        testCheckboxOff("zip");
    }
}