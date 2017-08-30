package io.micro.permissionsample.basic;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import io.micro.permissionsample.R;
import io.micro.permissionsample.util.Util;

/**
 * 1. Before run target action, need check whether has permission:
 * a. If has this permission, then run target action directly.
 * b. If no this permission , then invoke requestPermissions.
 * <p>
 * 2. After request Permission:
 * a. grant permission, then run target action.
 * b. deny permission and no checkbox , then show rationale.
 * c. deny permission and check checkbox, then show guide settings.
 */
public class BasicUsageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_usage);
    }

    // Invoked from this Activity
    public void clickCallPhone(View view) {
        call();
    }

    public static final int PERMISSION_CALL_PHONE = 1001;

    private void call() {
        String phone = "10086";
//        int result = PermissionChecker.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        // 授权之后每次调用都是直接通过的
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            callPhone(phone);
        } else {
            requestPhonePermission();
        }
    }

    private void callPhone(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestPhonePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_CALL_PHONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_CALL_PHONE:
                // 授权了
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call();
                } else {
                    boolean should = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE);
                    // true: 拒绝授权，但没有勾选【不再询问】
                    // false:拒绝授权，勾选了【不再询问】
                    if (should) {
                        showRationale();
                    } else {
                        showForeverRefuse();
                    }
                }
                break;

            default:
                // no-op
        }
    }

    private void showRationale() {
        new AlertDialog.Builder(this).setTitle("请允许拨打电话")
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPhonePermission();
                    }
                })
                .setNegativeButton("不允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(BasicUsageActivity.this, "不允许的话就不给执行", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private void showForeverRefuse() {
        new AlertDialog.Builder(this).setTitle("请允许使用电话权限,打开设置")
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toAppSettings();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void toAppSettings() {
        Util.toAppSettings(this);
    }
}
