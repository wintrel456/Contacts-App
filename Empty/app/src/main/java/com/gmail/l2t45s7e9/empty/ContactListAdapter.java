package com.gmail.l2t45s7e9.empty;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ContactListAdapter extends ArrayAdapter<Contact> {
    private final int[] colors = getContext().getResources().getIntArray(R.array.colors_list);
    private LayoutInflater inflater;
    private Contact[] contacts;
    private int layout;

    public ContactListAdapter(@NonNull Context context, int resource, @NonNull Contact[] objects) {
        super(context, resource, objects);
        this.inflater = LayoutInflater.from(context);
        this.contacts = objects;
        this.layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = inflater.inflate(this.layout, parent, false);
        TextView name = row.findViewById(R.id.userName);
        name.setText(contacts[position].getName());
        TextView number = row.findViewById(R.id.userNumber);
        number.setText(contacts[position].getFirstNumber());
        ImageView avatar = row.findViewById(R.id.avatar);
        if (contacts[position].getContactColor() == 0) {
            contacts[position].setContactColor(colors[(int) (Math.random() * colors.length)]);
        }
        avatar.setBackgroundTintList(ColorStateList.valueOf(contacts[position].getContactColor()));
        return row;
    }

}
