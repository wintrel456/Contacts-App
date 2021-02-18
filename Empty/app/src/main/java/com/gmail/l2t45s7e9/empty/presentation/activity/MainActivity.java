package com.gmail.l2t45s7e9.empty.presentation.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.gmail.l2t45s7e9.empty.R;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.navHost);
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
        int PERMISSIONS_REQUEST_READ_CONTACTS = 10;
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(
                    this,
                    R.string.permission_granted_toast_message,
                    Toast.LENGTH_SHORT
            ).show();
            navController.navigate(R.id.requestPermissionFragment);
        } else {
            Toast.makeText(
                    this,
                    R.string.permission_denied_toast_message,
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}