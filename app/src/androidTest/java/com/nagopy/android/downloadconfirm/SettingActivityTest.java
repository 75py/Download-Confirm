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
    public void test_clickHelpLink() throws Throwable {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_VIEW);
        intentFilter.addDataScheme("http");
        intentFilter.addDataPath("http://apps.nagopy.com/android/downloadconfirm.html", PatternMatcher.PATTERN_LITERAL);
        Instrumentation.ActivityMonitor monitor = new Instrumentation.ActivityMonitor(intentFilter, null, true);
        getInstrumentation().addMonitor(monitor);

        ListView listView = activity.findViewById(android.R.id.list);

        TouchUtils.dragViewToTop(this, listView); // 問題回避用のドラッグ
        onData(PreferenceMatchers.withTitle(R.string.help)).perform(click());

        // 確認
        assertEquals("ヘルプへのリンクをクリックしてintentが投げられることを確認", 1, monitor.getHits());

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