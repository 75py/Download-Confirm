/*
 * Copyright 2014 75py
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

package com.nagopy.android.downloadconfirm;

import android.app.Instrumentation;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PatternMatcher;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.PreferenceMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.ListView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;

/**
 * 設定画面のテストクラス.
 */
// API10で実行する場合はコメントアウトを解除すること
// @Ignore
@RunWith(AndroidJUnit4.class)
public class SettingActivityTest extends ActivityInstrumentationTestCase2<SettingActivity> {

    /**
     * テスト対象Activity
     */
    private SettingActivity activity;

    /**
     * コンストラクタ.
     */
    public SettingActivityTest() {
        super(SettingActivity.class);
    }


    /**
     * テスト実行前に実行されるメソッド.
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        activity = getActivity();
    }

    /**
     * ヘルプへのリンクをクリックしてIntentが飛ぶことを確認する.
     */
    @Test
    public void test_clickSampleLinks() throws Throwable {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_VIEW);
        intentFilter.addDataScheme("https");
        intentFilter.addDataPath("https://github.com/75py/Download-Confirm/wiki/Sample-links", PatternMatcher.PATTERN_LITERAL);
        Instrumentation.ActivityMonitor monitor = new Instrumentation.ActivityMonitor(intentFilter, null, true);
        getInstrumentation().addMonitor(monitor);

        ListView listView = activity.findViewById(android.R.id.list);

        TouchUtils.dragViewToTop(this, listView); // 問題回避用のドラッグ
        onData(PreferenceMatchers.withTitle(R.string.sample_links)).perform(click());

        // 確認
        assertEquals(1, monitor.getHits());

        // ActivityMonitor の削除
        getInstrumentation().removeMonitor(monitor);
    }

    /**
     * GitHubへのリンクをクリックしてIntentが飛ぶことを確認する.
     */
    @Test
    public void test_clickSourceLink() throws Throwable {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_VIEW);
        intentFilter.addDataScheme("https");
        intentFilter.addDataPath("https://github.com/75py/Download-Confirm/", PatternMatcher.PATTERN_LITERAL);
        Instrumentation.ActivityMonitor monitor = new Instrumentation.ActivityMonitor(intentFilter, null, true);
        getInstrumentation().addMonitor(monitor);

        ListView listView = activity.findViewById(android.R.id.list);

        TouchUtils.dragViewToTop(this, listView); // 問題回避用のドラッグ
        onData(PreferenceMatchers.withTitle(R.string.source)).perform(click());

        // 確認
        assertEquals("ソースコードへのリンクをクリックしてintentが投げられることを確認", 1, monitor.getHits());

        // ActivityMonitor の削除
        getInstrumentation().removeMonitor(monitor);
    }

}