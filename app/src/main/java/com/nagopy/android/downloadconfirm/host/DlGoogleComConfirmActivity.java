package com.nagopy.android.downloadconfirm.host;

import com.nagopy.android.downloadconfirm.AbstractConfirmActivity;
import com.nagopy.android.downloadconfirm.R;

public class DlGoogleComConfirmActivity extends AbstractConfirmActivity {

    @Override
    protected String getExtensionDisplayName() {
        return getString(R.string.host_dl_google_com);
    }
}
