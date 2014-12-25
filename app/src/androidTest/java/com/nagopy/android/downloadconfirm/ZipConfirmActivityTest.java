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
import android.widget.TextView;

import com.nagopy.android.downloadconfirm.extension.ZipConfirmActivity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;

/**
 * 設定画面のテストクラス.
 */
@RunWith(AndroidJUnit4.class)
public class ZipConfirmActivityTest extends ActivityInstrumentationTestCase2<ZipConfirmActivity> {

    /**
     * テスト対象Activity
     */
    private ZipConfirmActivity activity;
    /**
     * @link SharedPreferences
     */
    private SharedPreferences sp;

    /**
     * コンストラクタ.
     */
    public ZipConfirmActivityTest() {
        super(ZipConfirmActivity.class);
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

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("http://test.nagopy.com/dummy.zip"));
        setActivityIntent(intent);

        activity = getActivity();
        sp = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
    }

    @Test
    public void test_通常() throws Throwable {
        TextView url = (TextView) activity.findViewById(R.id.url);
        assertEquals("http://test.nagopy.com/dummy.zip", url.getText().toString());
    }

}