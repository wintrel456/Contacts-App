package com.gmail.l2t45s7e9.empty.presentation.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.gmail.l2t45s7e9.empty.R;
import com.gmail.l2t45s7e9.empty.entity.Contact;
import java.util.List;

public class ContactListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Contact> contacts;

    public void updateData(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public ContactListAdapter(Context context, List<Contact> contacts) {
        this.contacts = contacts;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.contact_list_item,
                    parent,
                    false
            );
        }
        Contact contact = (Contact) getItem(position);
        TextView name = convertView.findViewById(R.id.userName);
        name.setText(contact.getName());
        TextView number = convertView.findViewById(R.id.userNumber);
        number.setText(contact.getFirstNumber());
        ImageView avatar = convertView.findViewById(R.id.avatar);
        avatar.setBackgroundTintList(ColorStateList.valueOf(contacts.get(position).getContactColor()));
        return convertView;
    }

}
