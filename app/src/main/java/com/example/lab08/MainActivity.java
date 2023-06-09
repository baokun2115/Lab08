package com.example.lab08;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PERMISSION_REQUEST_CODE = 200;

    private View view;
    private Button checkPermission, requestPermission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission = findViewById(R.id.check_permission);
        requestPermission = findViewById(R.id.request_permission);

        checkPermission.setOnClickListener(this);
        requestPermission.setOnClickListener(this);
    }
    //Check permission
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        int result01 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        return (result == PackageManager.PERMISSION_GRANTED && result01 == PackageManager.PERMISSION_GRANTED);
    }

    //Request permission
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA
        }, PERMISSION_REQUEST_CODE);
    }
    @Override
    public void onClick(View v) {
        view = v;
        int id = view.getId();
        if (id == R.id.check_permission) {
            if (checkPermission()) {
                Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();
            }
        } else if (id == R.id.request_permission) {
            if (!checkPermission()) {
                requestPermission();
            } else {
                Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
            }
        }
    }
    //Result of request permission

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            //Get result by PERMISSION_REQUEST_CODE
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (locationAccepted && cameraAccepted) {
                        //Permission Granted
                        Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    } else {
                        //Permission Denied
                        Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
}