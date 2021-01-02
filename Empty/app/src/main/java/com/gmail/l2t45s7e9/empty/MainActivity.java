package com.gmail.l2t45s7e9.empty;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements ContactService.PublicServiceInterface {
    private ContactService contactService;
    private boolean isBound = false;
    private ServiceConnection serviceConnection;
    private boolean contactsIsRead = false;
    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.savedInstanceState = savedInstanceState;
        requestPermission(savedInstanceState);
    }

    public void loadContacts(final Bundle savedInstanceState) {
        final Intent intent = new Intent(this, ContactService.class);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                ContactService.LocalBinder localBinder = (ContactService.LocalBinder) iBinder;
                contactService = localBinder.getService();
                isBound = true;
                int position = getIntent().getIntExtra("notificationId", -1);
                String color = getIntent().getStringExtra("notificationColor");
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (position != -1) {
                    ContactDetailsFragment contactDetailsFragment = ContactDetailsFragment.newInstance(String.valueOf(position), Integer.parseInt(color));
                    fragmentManager.beginTransaction()
                            .add(R.id.mainFrame, contactDetailsFragment)
                            .commit();
                } else if (savedInstanceState == null) {
                    openContactList(fragmentManager);
                }
            }
            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                isBound = false;
            }
        };
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
        super.onDestroy();
    }

    @Override
    public ContactService getService() {
        if (isBound) {
            return contactService;
        } else {
            return null;
        }
    }

    public void openContactList(FragmentManager fragmentManager) {
        ContactListFragment contactListFragment = new ContactListFragment();
        fragmentManager.beginTransaction()
                .add(R.id.mainFrame, contactListFragment)
                .commit();
    }

    public void openNotification(FragmentManager fragmentManager) {
        ContactListFragment contactListFragment = new ContactListFragment();
        fragmentManager.beginTransaction()
                .add(R.id.mainFrame, contactListFragment)
                .commit();
    }

    public void requestPermission(Bundle savedInstanceState) {
        int hasReadContactPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            contactsIsRead = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }
        if (contactsIsRead) {
            loadContacts(savedInstanceState);
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
            loadContacts(savedInstanceState);
        } else {
            Toast.makeText(this, "Требуется установить разрешения", Toast.LENGTH_LONG).show();
        }
    }
}