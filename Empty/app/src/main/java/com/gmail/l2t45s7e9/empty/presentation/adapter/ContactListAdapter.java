package com.gmail.l2t45s7e9.empty.presentation.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.gmail.l2t45s7e9.empty.R;
import com.gmail.l2t45s7e9.empty.entity.Contact;
import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactViewHolder> implements Filterable {

    private List<Contact> contacts;
    private List<Contact> contactsFull;

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        contactsFull = new ArrayList<>(contacts);
        checkOnChange();
    }

    private void checkOnChange() {
        DiffUtil.DiffResult diffResult =
                DiffUtil.calculateDiff(new ContactDiffUtil(contacts, contactsFull), false);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.contact_list_item, parent, false);
        final ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        contactViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDetails(contactViewHolder.getAdapterPosition(), view);
            }
        });
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(contacts.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    private void openDetails(int position, View view) {
        String id = contacts.get(position).getId();
        int color = contacts.get(position).getContactColor();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putInt("color", color);
        Navigation.findNavController(view).navigate(R.id.action_contactListFragment_to_contactDetailsFragment, bundle);
    }

    @Override
    public Filter getFilter() {

        return new Filter() {

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                contacts.clear();
                contacts.addAll((List<Contact>) results.values);
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Contact> filteredList = new ArrayList<Contact>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(contactsFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Contact contact : contactsFull) {
                        if (contact.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(contact);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }
        };
    }

}
