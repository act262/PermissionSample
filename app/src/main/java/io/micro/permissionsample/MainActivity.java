package io.micro.permissionsample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import io.micro.permissionsample.basic.BasicUsageActivity;
import io.micro.permissionsample.dispatcher.DispatcherActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void checkPermission(View view) {
        int result = PermissionChecker.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Had granted permission.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No permission", Toast.LENGTH_SHORT).show();
        }
    }

    public void requestPermission(View view) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 100);
    }

    public void gotoBasicActivity(View view) {
        startActivity(new Intent(this, BasicUsageActivity.class));
    }

    public void gotoDispatcherActivity(View view) {
        startActivity(new Intent(this, DispatcherActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Request permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Request permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
