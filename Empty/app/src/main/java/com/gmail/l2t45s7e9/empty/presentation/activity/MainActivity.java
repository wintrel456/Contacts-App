package com.gmail.l2t45s7e9.empty.presentation.activity;

import android.Manifest;
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
    private boolean contactsIsRead = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
    }

    private void loadContacts() {
        String position = getIntent().getStringExtra("notificationId");
        int color = getIntent().getIntExtra("notificationColor", R.color.Font);
        NavController navController = Navigation.findNavController(this, R.id.navHost);
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
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                contactsIsRead = true;
            }
        }
        if (contactsIsRead) {
            loadContacts();
        } else {
            Toast.makeText(
                    this,
                    R.string.permission_toast_message,
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void requestPermission() {
        int hasReadContactPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            contactsIsRead = true;
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    1
            );
        }
        if (contactsIsRead) {
            loadContacts();
        }
    }
}