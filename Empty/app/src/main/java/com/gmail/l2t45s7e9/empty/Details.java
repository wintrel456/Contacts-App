package com.gmail.l2t45s7e9.empty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

public class Details extends Fragment implements CompoundButton.OnCheckedChangeListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(),R.layout.details, null);
        SwitchCompat switchCompat = view.findViewById(R.id.switch1);
        if(switchCompat!=null){
            switchCompat.setOnCheckedChangeListener(this);
        }
        return  view;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b){
            Toast toast = Toast.makeText(getContext(), "JOPA", Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(getContext(), "NOT JOPA", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
