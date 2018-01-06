package com.nagopy.android.downloadconfirm;

import android.content.Intent;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.nagopy.android.downloadconfirm.extension.ZipConfirmActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
     * コンストラクタ.
     */
    public ZipConfirmActivityTest() {
        super(ZipConfirmActivity.class);
    }


    /**
     * テスト実行前に実行されるメソッド.
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
    }

    @Test
    public void test_通常() throws Throwable {
        TextView url = activity.findViewById(R.id.url);
        assertEquals("http://test.nagopy.com/dummy.zip", url.getText().toString());
    }

}