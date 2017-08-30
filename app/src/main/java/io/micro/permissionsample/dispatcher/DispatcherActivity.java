package io.micro.permissionsample.dispatcher;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import io.micro.permissionsample.util.PermissionDispatcher;
import io.micro.permissionsample.R;
import io.micro.permissionsample.util.Util;

public class DispatcherActivity extends AppCompatActivity implements PermissionDispatcher.PermissionCallback {

    private PermissionDispatcher permissionDispatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatcher);
    }

    public void runMy(View view) {
        permissionDispatcher = PermissionDispatcher.create(this, Manifest.permission.CALL_PHONE, this);
        permissionDispatcher.check();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionDispatcher != null) {
            permissionDispatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showRationale() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("需要开启权限才能使用")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        permissionDispatcher.check();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DispatcherActivity.this, "not ok", Toast.LENGTH_SHORT).show();
                    }
                })
                .create();
        dialog.show();
    }

    private void showForeverRefuse() {
        new AlertDialog.Builder(this)
                .setTitle("去开启权限设置")
                .setPositiveButton("ok go go go", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Util.toAppSettings(DispatcherActivity.this);
                    }
                })
                .show();
    }

    private void callPhone() {
        Util.callPhone(this, "10086");
    }

    @Override
    public void onGrantedPermissions() {
        callPhone();
    }

    @Override
    public void onDeniedPermissions() {
        showRationale();
    }

    @Override
    public void onDeniedPermissionForever() {
        showForeverRefuse();
    }
}
