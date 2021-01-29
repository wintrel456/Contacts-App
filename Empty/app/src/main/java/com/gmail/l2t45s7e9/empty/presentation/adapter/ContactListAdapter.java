package com.gmail.l2t45s7e9.empty.presentation.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.gmail.l2t45s7e9.empty.R;
import com.gmail.l2t45s7e9.empty.entity.Contact;
import com.gmail.l2t45s7e9.empty.presentation.screens.ContactListFragment;

public class ContactListAdapter extends ListAdapter<Contact, ContactListAdapter.ContactViewHolder> {


    private Bundle bundle = new Bundle();
    private ContactListFragment contactListFragment = new ContactListFragment();

    public ContactListAdapter() {
        super(DIFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<Contact> DIFF_CALLBACK = new DiffUtil.ItemCallback<Contact>() {
        @Override
        public boolean areItemsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.getId().equals(newItem.getId()) &&
                    oldItem.getFirstNumber().equals(newItem.getFirstNumber()) &&
                    oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getContactColor() == newItem.getContactColor();
        }
    };

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.contact_list_item, parent, false);
        final ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        contactViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = contactViewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    String id = getItem(position).getId();
                    int color = getItem(position).getContactColor();
                    bundle.putString("id", id);
                    bundle.putInt("color", color);
                    contactListFragment.openDetails(bundle, view);
                }
            }
        });
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView number;
        private ImageView avatar;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userName);
            number = itemView.findViewById(R.id.userNumber);
            avatar = itemView.findViewById(R.id.avatar);
        }

        public void bind(Contact contact) {
            name.setText(contact.getName());
            number.setText(contact.getFirstNumber());
            avatar.setBackgroundTintList(ColorStateList.valueOf(contact.getContactColor()));
        }
    }

}
