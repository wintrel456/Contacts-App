package com.gmail.l2t45s7e9.empty;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ContactDetailsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private ContactService contactService;
    private SwitchCompat switchCompat;
    private int color;
    private String position;
    private TextView name;
    private TextView firstNumber;
    private TextView secondNumber;
    private TextView firstEmail;
    private TextView secondEmail;
    private TextView address;
    private ImageView avatar;
    private TextView birthDate;
    private GregorianCalendar date;
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    private Intent intent;
    private boolean isAlarmUp;

    public static ContactDetailsFragment newInstance(String id, int color) {
        ContactDetailsFragment contactDetailsFragment = new ContactDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putInt("color", color);
        contactDetailsFragment.setArguments(bundle);
        return contactDetailsFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        position = getArguments().getString("id");
        color = getArguments().getInt("color");
        super.onAttach(context);
        contactService = ((ContactService.PublicServiceInterface) context).getService();
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
        DetailsInformation callback = new DetailsInformation() {
            @Override
            public void getDetails(final Contact result) {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        avatar.setColorFilter(result.getContactColor());
                        name.setText(result.getName());
                        firstNumber.setText(result.getFirstNumber());
                        secondNumber.setText(result.getSecondNumber());
                        firstEmail.setText(result.getFirstEmail());
                        secondEmail.setText(result.getSecondEmail());
                        address.setText(result.getContactAddress());
                        date = (GregorianCalendar) result.getBirthDate();
                        if (date != null) {
                            birthDate.setText(String.format(Locale.getDefault(), "%d %s",
                                    date.get(Calendar.DATE), date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())).toUpperCase());
                        }

                    }
                });

            }
        };
        contactService.getDetailsInformation(callback, position, color);

        GradientDrawable drawable = (GradientDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.button, null);
        switchCompat = view.findViewById(R.id.notificationSwitch);
        TextView add = view.findViewById(R.id.addButton);

        drawable.setStroke(2, color);
        name.setSelected(true);
        add.setBackground(drawable);
        intent = new Intent(getContext(), ContactNotificationsReceiver.class);
        isAlarmUp = PendingIntent.getBroadcast(getContext(), Integer.parseInt(position), intent, PendingIntent.FLAG_NO_CREATE) != null;
        if (switchCompat != null) {
            switchCompat.setOnCheckedChangeListener(this);
            if (isAlarmUp) {
                switchCompat.setChecked(true);
            } else {
                switchCompat.setChecked(false);
            }

        }
    }

    public void initViews(View view) {
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
        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        intent.putExtra("name", name.getText());
        intent.putExtra("id", position);
        intent.putExtra("color", color);
        alarmIntent = PendingIntent.getBroadcast(getContext(), Integer.parseInt(position), intent, 0);
        if (isChecked) {
            if (!isAlarmUp) {
                setRepeating();
                Toast.makeText(getContext(), "Notification is ON", Toast.LENGTH_SHORT).show();
            }
            switchCompat.setThumbTintList(ColorStateList.valueOf(color));
            switchCompat.setTrackTintList(ColorStateList.valueOf(color).withAlpha(100));

        } else {
            if (PendingIntent.getBroadcast(getContext(), Integer.parseInt(position), intent, PendingIntent.FLAG_NO_CREATE) != null) {
                alarmManager.cancel(alarmIntent);
                alarmIntent.cancel();
                Toast.makeText(getContext(), "Notification is OFF", Toast.LENGTH_SHORT).show();
            }
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

    public void setRepeating() {
        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
        if (date.get(Calendar.DATE) == 29 && date.get(Calendar.MONTH) == Calendar.FEBRUARY) {
            if (calendar.isLeapYear(calendar.get(Calendar.YEAR))) {
                calendar.add(Calendar.YEAR, 4);
            }
        } else if (calendar.get(Calendar.MONTH) >= date.get(Calendar.MONTH) && calendar.get(Calendar.DATE) > date.get(Calendar.DATE)) {
            calendar.add(Calendar.YEAR, 1);
        }
        calendar.set(Calendar.MONTH, date.get(Calendar.MONTH));
        calendar.set(Calendar.DATE, date.get(Calendar.DATE));
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), alarmIntent);
    }
}
