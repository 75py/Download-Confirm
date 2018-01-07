/*
 * Copyright 2018 75py
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nagopy.android.downloadconfirm.extension;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nagopy.android.downloadconfirm.SettingActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.PreferenceMatchers.withTitleText;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public abstract class HookTest {

    private SettingActivity activity;

    @Rule
    public ActivityTestRule<SettingActivity> rule = new ActivityTestRule<>(SettingActivity.class);

    @Before
    public void setUp() throws Exception {
        activity = rule.getActivity();
    }

    protected void switchCheckbox(String preferenceText, boolean check) {
        try {
            onData(withTitleText(preferenceText))
                    .onChildView(withId(android.R.id.checkbox))
                    .check(ViewAssertions.matches(check ? isNotChecked() : isChecked()))
                    .perform(click());
        } catch (Throwable t) {
            // no op
        }
    }

    protected void testCheckboxOn(String extension, int activityLabelId) throws Throwable {
        onView(withId(android.R.id.list)).perform(swipeUp());
        Espresso.onIdle();

        switchCheckbox(extension.toUpperCase(), true);
        Espresso.onIdle();

        String[] schemes = {"http", "https"};
        String[] hosts = {"test.nagopy.com"};
        String[] dirs = {"/", "/foo/", "/foo/bar/"};
        String[] names = {"dummy"
                , "dummy1.dummy2"
                , "dummy1.dummy2"
                , "dummy1.dummy2.dummy3"
                , "dummy1.dummy2.dummy3.dummy4"
                , "dummy1.dummy2.dummy3.dummy4.dummy5"
                , "dummy1.dummy2.dummy3.dummy4.dummy5.dummy6"
                , "dummy1.dummy2.dummy3.dummy4.dummy5.dummy6.dummy7"
                , "dummy1.dummy2.dummy3.dummy4.dummy5.dummy6.dummy7.dummy8"
                , "dummy1.dummy2.dummy3.dummy4.dummy5.dummy6.dummy7.dummy8.dummy9"
                , "dummy1.dummy2.dummy3.dummy4.dummy5.dummy6.dummy7.dummy8.dummy9.dummy10"
                , "dummy1.dummy2.dummy3.dummy4.dummy5.dummy6.dummy7.dummy8.dummy9.dummy10.dummy11"
        };
        String[] exts = {extension.toLowerCase(), extension.toUpperCase()};
        for (String scheme : schemes) {
            for (String host : hosts) {
                for (String dir : dirs) {
                    for (String name : names) {
                        for (String ext : exts) {
                            String testUrl = scheme + "://" + host + dir + name + "." + ext;
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.setData(Uri.parse(testUrl));
                            PackageManager packageManager = activity.getPackageManager();
                            List<ResolveInfo> apps = packageManager.queryIntentActivities(intent, 0);
                            assertThat(apps, is(notNullValue()));
                            boolean ok = false;
                            for (ResolveInfo info : apps) {
                                if (info.activityInfo.packageName.equals(activity.getPackageName())) {
                                    assertThat(info.loadLabel(packageManager).toString(), is(activity.getString(activityLabelId)));
                                    ok = true; // 成功
                                    break;
                                }
                            }
                            if (!ok) {
                                fail("失敗 " + testUrl);
                            }
                        }
                    }
                }
            }
        }
    }

    protected void testCheckboxOff(String extension) throws Throwable {
        onView(withId(android.R.id.list)).perform(swipeUp());
        Espresso.onIdle();
        switchCheckbox(extension.toUpperCase(), false);
        Espresso.onIdle();

        String[] schemes = {"http", "https"};
        String[] hosts = {"test.nagopy.com"};
        String[] dirs = {"/", "/foo/", "/foo/bar/"};
        String[] names = {"dummy"
                , "dummy1.dummy2"
                , "dummy1.dummy2"
                , "dummy1.dummy2.dummy3"
                , "dummy1.dummy2.dummy3.dummy4"
                , "dummy1.dummy2.dummy3.dummy4.dummy5"
                , "dummy1.dummy2.dummy3.dummy4.dummy5.dummy6"
                , "dummy1.dummy2.dummy3.dummy4.dummy5.dummy6.dummy7"
                , "dummy1.dummy2.dummy3.dummy4.dummy5.dummy6.dummy7.dummy8"
                , "dummy1.dummy2.dummy3.dummy4.dummy5.dummy6.dummy7.dummy8.dummy9"
                , "dummy1.dummy2.dummy3.dummy4.dummy5.dummy6.dummy7.dummy8.dummy9.dummy10"
                , "dummy1.dummy2.dummy3.dummy4.dummy5.dummy6.dummy7.dummy8.dummy9.dummy10.dummy11"
        };
        String[] exts = {extension.toLowerCase(), extension.toUpperCase()};
        for (String scheme : schemes) {
            for (String host : hosts) {
                for (String dir : dirs) {
                    for (String name : names) {
                        for (String ext : exts) {
                            String testUrl = scheme + "://" + host + dir + name + "." + ext;
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent.setData(Uri.parse(testUrl));
                            PackageManager packageManager = activity.getPackageManager();
                            List<ResolveInfo> apps = packageManager.queryIntentActivities(intent, PackageManager.GET_META_DATA);
                            assertThat(apps, is(notNullValue()));
                            for (ResolveInfo info : apps) {
                                if (info.activityInfo.packageName.equals(activity.getPackageName())) {
                                    fail("無効にしたはずなのに有効になっている " + intent.getDataString());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}