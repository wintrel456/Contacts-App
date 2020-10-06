package com.gmail.l2t45s7e9.empty;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

public class ContactDetailsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private ContactService contactService;
    private SwitchCompat switchCompat;
    private int color;
    private int position;
    private TextView name;
    private TextView firstNumber;
    private TextView secondNumber;
    private TextView firstEmail;
    private TextView secondEmail;
    private TextView address;
    private ImageView avatar;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        contactService = ((ContactService.PublicServiceInterface) context).getService();
    }

    public void initViewsForDetails(View view) {
        name = view.findViewById(R.id.userName);
        firstNumber = view.findViewById(R.id.userNumber);
        secondNumber = view.findViewById(R.id.secondUserNumber);
        firstEmail = view.findViewById(R.id.firstEmail);
        secondEmail = view.findViewById(R.id.secondEmail);
        address = view.findViewById(R.id.address);
        avatar = view.findViewById(R.id.avatar);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle id = getArguments();
        if (id != null) {
            position = id.getInt("id");
        }
        initViewsForDetails(view);
        final GradientDrawable drawable = (GradientDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.button, null);
        DetailsInformation callback = new DetailsInformation() {
            @Override
            public void getDetails(Contact result) {
                name.setText(result.getName());
                firstNumber.setText(result.getFirstNumber());
                secondNumber.setText(result.getSecondNumber());
                firstEmail.setText(result.getFirstEmail());
                secondEmail.setText(result.getSecondEmail());
                address.setText(result.getContactAddress());
                avatar.setColorFilter(result.getContactColor());
                color = result.getContactColor();
                drawable.setStroke(2, color);
            }
        };
        contactService.getDetailsInformation(callback, position);
        switchCompat = view.findViewById(R.id.notificationSwitch);
        TextView add = view.findViewById(R.id.addButton);
        name.setSelected(true);
        add.setBackground(drawable);
        if (switchCompat != null) {
            switchCompat.setOnCheckedChangeListener(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_details_fragment, null);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            switchCompat.setThumbTintList(ColorStateList.valueOf(color));
            switchCompat.setTrackTintList(ColorStateList.valueOf(color).withAlpha(100));
            Toast.makeText(getContext(), "Notification is ON", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Notification is OFF", Toast.LENGTH_SHORT).show();
            switchCompat.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.side_color)));
            switchCompat.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.second_side_color)));
        }
    }

    @Override
    public void onDestroyView() {
        switchCompat = null;
        name = null;
        firstNumber = null;
        secondNumber = null;
        firstEmail = null;
        secondEmail = null;
        address = null;
        avatar = null;
        super.onDestroyView();
    }

    interface DetailsInformation {
        void getDetails(Contact contact);
    }
}
