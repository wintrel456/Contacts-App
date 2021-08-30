package com.gmail.l2t45s7e9.library.presentation.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.l2t45s7e9.java.entity.Contact;
import com.gmail.l2t45s7e9.library.R;

public class ContactListAdapter extends ListAdapter<Contact, ContactListAdapter.ContactViewHolder> {

    public static final DiffUtil.ItemCallback<Contact> DIFF_CALLBACK = new DiffUtil.ItemCallback<Contact>() {
        @Override
        public boolean areItemsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.getId().equals(newItem.getId()) &&
                    oldItem.getFirstNumber().equals(newItem.getFirstNumber()) &&
                    oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getContactColor() == newItem.getContactColor();
        }
    };
    private OnItemClickListener onItemClickListener;

    public ContactListAdapter(OnItemClickListener onItemClickListener) {
        super(DIFF_CALLBACK);
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.contact_list_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(getItem(position), onItemClickListener);
    }

    public interface OnItemClickListener {
        void onItemClicked(Contact contact);
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

        public void bind(final Contact contact, final OnItemClickListener onItemClickListener) {
            name.setText(contact.getName());
            number.setText(contact.getFirstNumber());
            avatar.setBackgroundTintList(ColorStateList.valueOf(contact.getContactColor()));
            itemView.setOnClickListener(view -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClicked(contact);
                }

            });
        }
    }

}
