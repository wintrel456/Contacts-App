package com.gmail.l2t45s7e9.library.presentation.screens;

import android.app.Application;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.gmail.l2t45s7e9.library.R;
import com.gmail.l2t45s7e9.library.domain.ContactDetailsViewModel;
import com.gmail.l2t45s7e9.library.domain.factories.ViewModelDetailsFactory;
import com.gmail.l2t45s7e9.library.interfaces.ContactDetailsContainer;
import com.gmail.l2t45s7e9.library.interfaces.HasAppContainer;
import com.google.android.material.snackbar.Snackbar;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.inject.Inject;

public class ContactDetailsFragment extends Fragment implements
        CompoundButton.OnCheckedChangeListener, AddressSearchFragment.OnChooseAddress {

    @Inject
    ViewModelDetailsFactory viewModelDetailsFactory;
    private ContactDetailsViewModel contactDetailsViewModel;
    private SwitchCompat switchCompat;
    private int color;
    private String position;
    private TextView name;
    private TextView firstNumber;
    private TextView secondNumber;
    private TextView firstEmail;
    private TextView secondEmail;
    private TextView address;
    private TextView add;
    private ImageView avatar;
    private TextView birthDate;
    private GregorianCalendar date;
    private String formatDate;
    private String addressString;
    private boolean addressState;

    private TextView.OnClickListener onClickListener = view -> {
        Bundle bundle = new Bundle();
        bundle.putString("id", position);
        if (!addressState) {
            FragmentManager fragmentManager = getFragmentManager();
            AddressSearchFragment addressSearchFragment = new AddressSearchFragment();
            addressSearchFragment.setArguments(bundle);
            addressSearchFragment.show(fragmentManager, "SearchAddress");
        } else {
            Navigation.findNavController(view).navigate(R.id.action_contactDetailsFragment_to_mapFragment, bundle);
        }
    };

    @Override
    public void onChoose() {
        contactDetailsViewModel.loadContactDetails(position, color);
        Snackbar.make(getView(), R.string.address_added, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Application app = requireActivity().getApplication();
        if (!(app instanceof HasAppContainer)) {
            throw new IllegalStateException();
        }
        ContactDetailsContainer contactDetailsContainer = ((HasAppContainer) app).appContainer()
                .plusContactDetailsContainer();
        contactDetailsContainer.inject(this);
        super.onAttach(context);
        position = getArguments().getString("id");
        color = getArguments().getInt("color");
        formatDate = getResources().getString(R.string.date_format_for_contact_details);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_details_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        contactDetailsViewModel = new ViewModelProvider(
                this, viewModelDetailsFactory
        ).get(ContactDetailsViewModel.class);
        contactDetailsViewModel.contactDetailsLiveData.observe(
                getViewLifecycleOwner(), result -> {
                    position = result.getId();
                    avatar.setColorFilter(result.getContactColor());
                    name.setText(result.getName());
                    firstNumber.setText(result.getFirstNumber());
                    secondNumber.setText(result.getSecondNumber());
                    firstEmail.setText(result.getFirstEmail());
                    secondEmail.setText(result.getSecondEmail());
                    addressString = result.getContactAddress();
                    if (addressString.length() != 0) {
                        addressState = true;
                        address.setText(addressString);
                        add.setText(getString(R.string.see_on_map_label));
                    } else {
                        addressState = false;
                        address.setText(getString(R.string.empty_address));
                        add.setText(getString(R.string.button_add));
                    }

                    date = result.getBirthDate();
                    if (date != null) {
                        birthDate.setText(
                                String.format(Locale.getDefault(),
                                        formatDate,
                                        date.get(
                                                Calendar.DATE),
                                        date.getDisplayName(
                                                Calendar.MONTH, Calendar.LONG,
                                                Locale.getDefault())).toUpperCase()
                        );
                    } else {
                        birthDate.setText(R.string.empty_date);
                    }
                    color = result.getContactColor();
                    setSwitchCompat();
                }
        );
        contactDetailsViewModel.loadContactDetails(position, color);
        GradientDrawable drawable = (GradientDrawable) ResourcesCompat.getDrawable(
                getResources(),
                R.drawable.button,
                null
        );
        add.setOnClickListener(onClickListener);
        drawable.setStroke(2, color);
        name.setSelected(true);
        add.setBackground(drawable);
    }

    private void setSwitchCompat() {
        if (date != null) {
            if (switchCompat != null) {
                switchCompat.setOnCheckedChangeListener(this);
                if (contactDetailsViewModel.getStatus()) {
                    switchCompat.setChecked(true);
                } else {
                    switchCompat.setChecked(false);
                }
            }
        }
    }

    private void initViews(View view) {
        switchCompat = view.findViewById(R.id.notificationSwitch);
        add = view.findViewById(R.id.addButton);
        name = view.findViewById(R.id.userName);
        firstNumber = view.findViewById(R.id.userNumber);
        secondNumber = view.findViewById(R.id.secondUserNumber);
        firstEmail = view.findViewById(R.id.firstEmail);
        secondEmail = view.findViewById(R.id.secondEmail);
        address = view.findViewById(R.id.address);
        avatar = view.findViewById(R.id.avatar);
        birthDate = view.findViewById(R.id.birhDate);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            switchCompat.setThumbTintList(ColorStateList.valueOf(color));
            switchCompat.setTrackTintList(ColorStateList.valueOf(color).withAlpha(100));

            if (!contactDetailsViewModel.getStatus()) {
                contactDetailsViewModel.setNotification();
                Snackbar.make(getView(), R.string.on_notification_toast_message, Snackbar.LENGTH_SHORT).show();
            }
        } else {
            switchCompat.setThumbTintList(
                    ColorStateList.valueOf(getResources().getColor(R.color.side_color))
            );
            switchCompat.setTrackTintList(
                    ColorStateList.valueOf(getResources().getColor(R.color.second_side_color))
            );

            if (contactDetailsViewModel.getStatus()) {
                contactDetailsViewModel.cancelNotification();
                Snackbar.make(getView(), R.string.off_notification_toast_message, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        add = null;
        switchCompat = null;
        name = null;
        firstNumber = null;
        secondNumber = null;
        firstEmail = null;
        secondEmail = null;
        address = null;
        avatar = null;
        birthDate = null;
        super.onDestroyView();
    }


}
