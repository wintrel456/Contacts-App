package com.gmail.l2t45s7e9.empty;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import java.util.List;

public class ContactListFragment extends ListFragment {

    private ContactService contactService;
    private List<Contact> contacts;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        contactService = ((ContactService.PublicServiceInterface) context).getService();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_list_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView count = view.findViewById(R.id.contactCount);
        ShortInformation callback = new ShortInformation() {
            @Override
            public void getContactList(final List<Contact> result) {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        contacts = result;
                        count.setText(String.valueOf(result.size()));
                        ContactListAdapter adapter = new ContactListAdapter(getActivity(), R.layout.contact_list_item, result);
                        setListAdapter(adapter);
                    }
                });
            }
        };
        contactService.getShortInformation(callback);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        openDetails(position);
    }

    public void openDetails(int position) {
        String id = contacts.get(position).getId();
        int color = contacts.get(position).getContactColor();
        ContactDetailsFragment contactDetailsFragment = ContactDetailsFragment.newInstance(id, color);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, contactDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    interface ShortInformation {
        void getContactList(List<Contact> result);
    }

}