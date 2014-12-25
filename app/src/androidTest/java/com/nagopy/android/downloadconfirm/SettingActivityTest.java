package com.nagopy.android.downloadconfirm;

import android.app.Instrumentation;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.PatternMatcher;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.PreferenceMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.ListView;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

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
     * @link SharedPreferences
     */
    private SharedPreferences sp;

    /**
     * コンストラクタ.
     */
    public SettingActivityTest() {
        super(SettingActivity.class);
    }


    /**
     * テスト実行前に実行されるメソッド.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        activity = getActivity();
        sp = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
    }


    // チェックボックスをONにして有効になっていることを確認
    @Test
    public void test_apkCheckboxOn() throws Throwable {
        testCheckboxOn("apk", R.string.label_apk);
    }

    @Test
    public void test_pdfCheckboxOn() throws Throwable {
        testCheckboxOn("pdf", R.string.label_pdf);
    }

    @Test
    public void test_tgzCheckboxOn() throws Throwable {
        testCheckboxOn("tgz", R.string.label_tgz);
    }

    @Test
    public void test_zipCheckboxOn() throws Throwable {
        testCheckboxOn("zip", R.string.label_zip);
    }

    // チェックボックスをOFFにして無効になっていることを確認
    @Test
    public void test_apkCheckboxOff() throws Throwable {
        testCheckboxOff("apk");
    }

    @Test
    public void test_pdfCheckboxOff() throws Throwable {
        testCheckboxOff("pdf");
    }

    @Test
    public void test_tgzCheckboxOff() throws Throwable {
        testCheckboxOff("tgz");
    }

    @Test
    public void test_zipCheckboxOff() throws Throwable {
        testCheckboxOff("zip");
    }


    /**
     * 拡張子のチェックを入れて有効になっていることを確認する.
     *
     * @param extension       拡張子
     * @param activityLabelId リンククリック時に起動するActivityのラベルのリソースID
     * @throws Throwable テスト失敗時
     */
    private void testCheckboxOn(String extension, int activityLabelId) throws Throwable {
        ListView listView = (ListView) activity.findViewById(android.R.id.list);

        TouchUtils.dragViewToTop(this, listView); // 問題回避用のドラッグ

        boolean isEnabled = sp.getBoolean(".extension."
                + extension.substring(0, 1).toUpperCase() + extension.substring(1, extension.length()).toLowerCase(), true);
        if (!isEnabled) { // 無効になっている場合は有効にする
            onData(Matchers.<Object>allOf(PreferenceMatchers.withTitleText(extension.toUpperCase()))).perform(ViewActions.click());
        }

        { // HTTP
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("http://test.nagopy.com/dummy." + extension.toLowerCase()));
            PackageManager packageManager = activity.getPackageManager();
            List<ResolveInfo> apps = packageManager.queryIntentActivities(intent, PackageManager.GET_META_DATA);
            assertNotNull(apps);
            boolean ok = false;
            for (ResolveInfo info : apps) {
                if (info.activityInfo.packageName.equals(activity.getPackageName())) {
                    assertEquals("ラベルを確認", info.loadLabel(packageManager), activity.getString(activityLabelId));
                    ok = true; // 成功
                    break;
                }
            }
            if (!ok) {
                fail("有効になっていない");
            }
        }
        { // HTTPS
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://test.nagopy.com/aaa.bbb.あああ.123." + extension.toLowerCase()));
            PackageManager packageManager = activity.getPackageManager();
            List<ResolveInfo> apps = packageManager.queryIntentActivities(intent, PackageManager.GET_META_DATA);
            assertNotNull(apps);
            boolean ok = false;
            for (ResolveInfo info : apps) {
                if (info.activityInfo.packageName.equals(activity.getPackageName())) {
                    assertEquals("ラベルを確認", info.loadLabel(packageManager), activity.getString(activityLabelId));
                    ok = true; // 成功
                    break;
                }
            }
            if (!ok) {
                fail("有効になっていない");
            }
        }
    }

    /**
     * 拡張子のチェックを外して無効になっていることを確認する.
     *
     * @param extension 拡張子
     * @throws Throwable テスト失敗時
     */
    private void testCheckboxOff(String extension) throws Throwable {
        ListView listView = (ListView) activity.findViewById(android.R.id.list);

        TouchUtils.dragViewToTop(this, listView); // 問題回避用のドラッグ

        boolean isEnabled = sp.getBoolean(".extension."
                + extension.substring(0, 1).toUpperCase() + extension.substring(1, extension.length()).toLowerCase(), true);
        if (isEnabled) { // 有効になっている場合は無効にする
            onData(Matchers.<Object>allOf(PreferenceMatchers.withTitleText(extension.toUpperCase()))).perform(ViewActions.click());
        }

        { // HTTP
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("http://test.nagopy.com/dummy." + extension.toLowerCase()));
            PackageManager packageManager = activity.getPackageManager();
            List<ResolveInfo> apps = packageManager.queryIntentActivities(intent, PackageManager.GET_META_DATA);
            assertNotNull(apps);
            for (ResolveInfo info : apps) {
                if (info.activityInfo.packageName.equals(activity.getPackageName())) {
                    fail("無効にしたはずなのに有効になっている " + intent.getDataString());
                }
            }
        }
        { // HTTPS
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://test.nagopy.com/dummy." + extension.toLowerCase()));
            PackageManager packageManager = activity.getPackageManager();
            List<ResolveInfo> apps = packageManager.queryIntentActivities(intent, PackageManager.GET_META_DATA);
            assertNotNull(apps);
            for (ResolveInfo info : apps) {
                if (info.activityInfo.packageName.equals(activity.getPackageName())) {
                    fail("無効にしたはずなのに有効になっている " + intent.getDataString());
                }
            }
        }
    }

    /**
     * ヘルプへのリンクをクリックしてIntentが飛ぶことを確認する.
     *
     * @throws Throwable
     */
    @Test
    public void test_clickHelpLink() throws Throwable {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_VIEW);
        intentFilter.addDataScheme("http");
        intentFilter.addDataPath("http://apps.nagopy.com/android/downloadconfirm.html", PatternMatcher.PATTERN_LITERAL);
        Instrumentation.ActivityMonitor monitor = new Instrumentation.ActivityMonitor(intentFilter, null, true);
        getInstrumentation().addMonitor(monitor);

        ListView listView = (ListView) activity.findViewById(android.R.id.list);

        TouchUtils.dragViewToTop(this, listView); // 問題回避用のドラッグ
        onData(Matchers.<Object>allOf(PreferenceMatchers.withTitle(R.string.help))).perform(click());

        // 確認
        assertEquals("ヘルプへのリンクをクリックしてintentが投げられることを確認", 1, monitor.getHits());

        // ActivityMonitor の削除
        getInstrumentation().removeMonitor(monitor);
    }

    /**
     * GithubへのリンクをクリックしてIntentが飛ぶことを確認する.
     *
     * @throws Throwable
     */
    @Test
    public void test_clickSourceLink() throws Throwable {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_VIEW);
        intentFilter.addDataScheme("https");
        intentFilter.addDataPath("https://github.com/75py/Download-Confirm/", PatternMatcher.PATTERN_LITERAL);
        Instrumentation.ActivityMonitor monitor = new Instrumentation.ActivityMonitor(intentFilter, null, true);
        getInstrumentation().addMonitor(monitor);

        ListView listView = (ListView) activity.findViewById(android.R.id.list);

        TouchUtils.dragViewToTop(this, listView); // 問題回避用のドラッグ
        onData(Matchers.<Object>allOf(PreferenceMatchers.withTitle(R.string.source))).perform(click());

        // 確認
        assertEquals("ソースコードへのリンクをクリックしてintentが投げられることを確認", 1, monitor.getHits());

        // ActivityMonitor の削除
        getInstrumentation().removeMonitor(monitor);
    }

}