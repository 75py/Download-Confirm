package com.nagopy.android.downloadconfirm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public abstract class AbstractConfirmActivity extends ActionBarActivity implements View.OnClickListener {

    protected Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.app_name);

        intent = (Intent) getIntent().clone();
        intent.setComponent(null);

        setContentView(R.layout.activity_confirm);
        getWindow().setLayout(getDisplayWidth() - (getResources().getDimensionPixelSize(R.dimen.dialog_horizontal_margin) * 2)
                , ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView url = (TextView) findViewById(R.id.url);
        url.setText(intent.getDataString());


        findViewById(R.id.cancel).setOnClickListener(this);
        View downloadButton = findViewById(R.id.download);
        downloadButton.setOnClickListener(this);

        TextView message = (TextView) findViewById(R.id.message);
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> apps = packageManager.queryIntentActivities(intent, PackageManager.GET_META_DATA);
        if (apps == null || apps.isEmpty()) {
            downloadButton.setEnabled(false);
            message.setText(getString(R.string.confirm_message, getExtensionDisplayName(), getString(R.string.confirm_message_sub_not_found_apps)));
        } else {
            for (Iterator<ResolveInfo> iterator = apps.iterator(); iterator.hasNext(); ) {
                ResolveInfo info = iterator.next();
                if (info.activityInfo.packageName.equals(getPackageName())) {
                    iterator.remove();
                }
            }

            int downloadableApps = apps.size();
            if (downloadableApps == 0) {
                downloadButton.setEnabled(false);
                message.setText(getString(R.string.confirm_message, getExtensionDisplayName(), getString(R.string.confirm_message_sub_not_found_apps)));
            } else if (downloadableApps == 1) {
                downloadButton.setEnabled(true);
                message.setText(getString(R.string.confirm_message, getExtensionDisplayName(), getString(R.string.confirm_message_sub_one_app)));
            } else {
                downloadButton.setEnabled(true);
                message.setText(getString(R.string.confirm_message, getExtensionDisplayName(), getString(R.string.confirm_message_sub_many_apps)));
            }
        }
    }

    protected String getExtensionDisplayName() {
        return this.getClass().getSimpleName().replace("ConfirmActivity", "").toUpperCase(Locale.getDefault());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.download:
                setDisabled();
                startActivity(intent);

                setEnabledDelayed();
                finish();
                break;
            default:
                throw new IllegalArgumentException("unknown id was clicked. id=" + v.getId());
        }
    }

    protected void setDisabled() {
        PackageManager packageManager = getPackageManager();
        ComponentName componentName = new ComponentName(getApplicationContext(), getClass());
        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    protected void setEnabledDelayed() {
        Intent service = new Intent(getApplicationContext(), PackageManagementService.class);
        service.putExtra("className", getClass().getName());
        startService(service);
    }

    protected int getDisplayWidth() {
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point outSize = new Point();
            windowManager.getDefaultDisplay().getSize(outSize);
            return outSize.x;
        } else {
            return windowManager.getDefaultDisplay().getWidth();
        }
    }
}
