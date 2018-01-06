package com.nagopy.android.downloadconfirm.extension;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nagopy.android.downloadconfirm.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * 設定画面のテストクラス.
 */
@RunWith(AndroidJUnit4.class)
public class ZipConfirmActivityTest {

    @Rule
    public ActivityTestRule<ZipConfirmActivity> rule
            = new ActivityTestRule<>(ZipConfirmActivity.class, false, false);

    @Before
    public void setUp() throws Exception {
        InstrumentationRegistry.getTargetContext()
                .getPackageManager()
                .setComponentEnabledSetting(
                        new ComponentName(InstrumentationRegistry.getTargetContext(), ZipConfirmActivity.class)
                        , PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        , PackageManager.DONT_KILL_APP
                );
    }

    @Test
    public void test() throws Throwable {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("http://test.nagopy.com/dummy.zip"));

        ZipConfirmActivity activity = rule.launchActivity(intent);

        assertThat(activity, is(notNullValue()));
        onView(withId(R.id.url)).check(matches(withText("http://test.nagopy.com/dummy.zip")));
    }

}