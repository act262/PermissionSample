package io.micro.permissionsample.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * Auto select vendor permission settings intent.
 */
public class PermissionSettingsIntent {

    public Intent getVendorIntent(Context context) {
        if (Build.MANUFACTURER.equalsIgnoreCase("huawei")) {
            return huawei();
        } else if (Build.MANUFACTURER.equalsIgnoreCase("xiaomi")) {
            return xiaomi(context);
        } else if (Build.MANUFACTURER.equalsIgnoreCase("meizu")) {
            return meizu(context);
        } else if (Build.MANUFACTURER.equalsIgnoreCase("htc")) {
            return htc(context);
        }

        // Default intent
        return defaultIntent(context);
    }

    public Intent defaultIntent(Context context) {
        return new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", context.getPackageName(), null));
    }

    private Intent huawei() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
        intent.setComponent(comp);
        return intent;
    }

    private Intent xiaomi(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.setComponent(componentName);
        intent.putExtra("extra_pkgname", context.getPackageName());
        return intent;
    }

    private Intent meizu(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", context.getPackageName());
        return intent;
    }

    private Intent htc(Context context) {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.htc.htcappopsguarddog", "com.htc.htcappopsguarddog.HtcAppOpsDetailsActivity");
        intent.setComponent(componentName);
        intent.putExtra("package", context.getPackageName());
        intent.putExtra("appops_caller", "AppOpsCategory");
        intent.putExtra("appops_caller_in_settings_app", false);
        return intent;
    }
}
