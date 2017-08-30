package io.micro.permissionsample.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

/**
 * Permission dispatcher
 */
public class PermissionDispatcher {

    public interface PermissionCallback {
        void onGrantedPermissions();

        void onDeniedPermissions();

        void onDeniedPermissionForever();
    }

    private Activity activity;
    private String permission;
    private int requestCode;
    private PermissionCallback callback;

    public static PermissionDispatcher create(Activity activity, String permission, PermissionCallback callback) {
        int code = (int) (Math.random() + 1000);
        return create(activity, permission, code, callback);
    }

    public static PermissionDispatcher create(Activity activity, String permission, int requestCode, PermissionCallback callback) {
        PermissionDispatcher permissionDispatcher = new PermissionDispatcher();
        permissionDispatcher.activity = activity;
        permissionDispatcher.permission = permission;
        permissionDispatcher.requestCode = requestCode;
        permissionDispatcher.callback = callback;
        return permissionDispatcher;
    }

    public void check() {
        int result = ActivityCompat.checkSelfPermission(activity, permission);
        if (result == PackageManager.PERMISSION_GRANTED) {
            callback.onGrantedPermissions();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != this.requestCode) return;

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            callback.onGrantedPermissions();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                callback.onDeniedPermissions();
            } else {
                callback.onDeniedPermissionForever();
            }
        }

    }
}
