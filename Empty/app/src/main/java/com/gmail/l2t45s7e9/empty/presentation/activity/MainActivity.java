package com.gmail.l2t45s7e9.empty.presentation.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.gmail.l2t45s7e9.empty.R;

public class MainActivity extends AppCompatActivity {
    private final int PERMISSIONS_REQUEST_READ_CONTACTS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_CONTACTS) ==
                PackageManager.PERMISSION_GRANTED) {
            loadContacts();

        } else {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS);
        }

    }

    private void loadContacts() {
        NavController navController = Navigation.findNavController(this, R.id.navHost);
        String position = getIntent().getStringExtra("notificationId");
        int color = getIntent().getIntExtra("notificationColor", R.color.Font);
        if (position != null) {
            Bundle bundle = new Bundle();
            bundle.putString("id", position);
            bundle.putInt("color", color);
            navController.navigate(R.id.action_contactListFragment_to_contactDetailsFragment, bundle);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(
                    this,
                    R.string.permission_granted_toast_message,
                    Toast.LENGTH_LONG
            ).show();
            restartAppWithGrantedPermission();
        } else {
            Toast.makeText(
                    this,
                    R.string.permission_denied_toast_message,
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    public void restartAppWithGrantedPermission() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}