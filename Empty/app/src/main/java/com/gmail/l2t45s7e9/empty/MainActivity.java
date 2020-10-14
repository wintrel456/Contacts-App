package com.gmail.l2t45s7e9.empty;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements ContactService.PublicServiceInterface {
    private ContactService contactService;
    boolean isBound = false;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, ContactService.class);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                ContactService.LocalBinder localBinder = (ContactService.LocalBinder) iBinder;
                contactService = localBinder.getService();
                isBound = true;
                int position = getIntent().getIntExtra("id", -1);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (savedInstanceState == null && position == -1) {
                    ContactListFragment contactListFragment = new ContactListFragment();
                    fragmentTransaction.add(R.id.mainFrame, contactListFragment)
                            .commit();
                }
                if (position != -1) {
                    ContactDetailsFragment contactDetailsFragment = ContactDetailsFragment.newInstance(position);
                    fragmentTransaction.replace(R.id.mainFrame, contactDetailsFragment)
                            .commit();
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
}