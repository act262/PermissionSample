package io.micro.permissionsample.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * Common Utility
 */
public class Util {

    // 打开App设置页面，可以根据不同的厂商特殊处理,比如小米可以直接到权限设置等页面
    public static Intent getAppSettingsIntent(Context context) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Default intent
        return new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", context.getPackageName(), null));
    }

    public static void toAppSettings(Context context) {
        PermissionSettingsIntent permissionSettings = new PermissionSettingsIntent();
        try {
            context.startActivity(permissionSettings.getVendorIntent(context));
        } catch (Exception e) {
            e.printStackTrace();
            context.startActivity(permissionSettings.defaultIntent(context));
        }
    }

    public static void callPhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
