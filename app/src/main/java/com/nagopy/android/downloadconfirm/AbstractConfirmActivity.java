package com.nagopy.android.downloadconfirm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

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

        TextView message = (TextView) findViewById(R.id.message);
        message.setText(getString(R.string.confirm_message, getExtensionDisplayName()));

        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.download).setOnClickListener(this);
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

    protected void setDisabled(){
        PackageManager packageManager = getPackageManager();
        ComponentName componentName = new ComponentName(getApplicationContext(), getClass());
        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    protected void setEnabledDelayed(){
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
