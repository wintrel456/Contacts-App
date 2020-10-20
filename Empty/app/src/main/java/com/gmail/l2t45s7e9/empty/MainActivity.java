package com.gmail.l2t45s7e9.empty;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements ContactService.PublicServiceInterface {
    private ContactService contactService;
    private boolean isBound = false;
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
                int position = getIntent().getIntExtra("notificationId", -1);
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (savedInstanceState == null && position == -1) {
                    openContactList(fragmentManager);
                }
                if (position != -1) {
                    openContactList(fragmentManager);
                    ContactDetailsFragment contactDetailsFragment = ContactDetailsFragment.newInstance(position);
                    fragmentManager.beginTransaction()
                            .replace(R.id.mainFrame, contactDetailsFragment)
                            .addToBackStack(null)
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

    public void openContactList(FragmentManager fragmentManager) {
        ContactListFragment contactListFragment = new ContactListFragment();
        fragmentManager.beginTransaction()
                .add(R.id.mainFrame, contactListFragment)
                .commit();
    }
}