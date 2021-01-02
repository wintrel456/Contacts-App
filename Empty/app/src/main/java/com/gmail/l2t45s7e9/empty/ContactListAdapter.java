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

import java.util.List;

public class ContactListAdapter extends ArrayAdapter<Contact> {
    private LayoutInflater inflater;
    private List<Contact> contacts;
    private int layout;

    public ContactListAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
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
        name.setText(contacts.get(position).getName());
        TextView number = row.findViewById(R.id.userNumber);
        number.setText(contacts.get(position).getFirstNumber());
        ImageView avatar = row.findViewById(R.id.avatar);
        avatar.setBackgroundTintList(ColorStateList.valueOf(contacts.get(position).getContactColor()));
        return row;
    }

}
