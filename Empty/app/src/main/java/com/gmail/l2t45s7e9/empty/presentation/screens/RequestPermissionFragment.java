package com.gmail.l2t45s7e9.empty.presentation.screens;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.gmail.l2t45s7e9.empty.R;

public class RequestPermissionFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request_permission, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int PERMISSIONS_REQUEST_READ_CONTACTS = 10;
        if (ActivityCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.READ_CONTACTS) ==
                PackageManager.PERMISSION_GRANTED) {
            Navigation.findNavController(getView()).navigate(R.id.action_requestPermissionFragment_to_contactListFragment);
        } else {
            getActivity().requestPermissions(
                    new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS
            );
        }
    }
}