package com.nagopy.android.downloadconfirm.host;

import com.nagopy.android.downloadconfirm.AbstractConfirmActivity;
import com.nagopy.android.downloadconfirm.R;

public class AndroidClientsGoogleComConfirmActivity extends AbstractConfirmActivity {

    @Override
    protected String getExtensionDisplayName() {
        return getString(R.string.host_android_clients_google_com);
    }
}
