package com.gmail.l2t45s7e9.empty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

public class ContactDetailsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private Contact contact;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_details_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle id = getArguments();
        if (id != null) {
            contact = id.getParcelable("listItem");
        }
        TextView name = view.findViewById(R.id.userName);
        name.setText(contact.getName());
        TextView firstNumber = view.findViewById(R.id.userNumber);
        firstNumber.setText(contact.getFirstNumber());
        TextView secondNumber = view.findViewById(R.id.secondUserNumber);
        secondNumber.setText(contact.getSecondNumber());
        TextView firstEmail = view.findViewById(R.id.firstEmail);
        firstEmail.setText(contact.getFirstEmail());
        TextView secondEmail = view.findViewById(R.id.secondEmail);
        secondEmail.setText(contact.getSecondEmail());
        TextView address = view.findViewById(R.id.address);
        address.setText(contact.getContactAddress());
        SwitchCompat switchCompat = view.findViewById(R.id.notificationSwitch);
        if (switchCompat != null) {
            switchCompat.setOnCheckedChangeListener(this);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            Toast.makeText(getContext(), "Notification is ON", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Notification is OFF", Toast.LENGTH_SHORT).show();
        }
    }
}
