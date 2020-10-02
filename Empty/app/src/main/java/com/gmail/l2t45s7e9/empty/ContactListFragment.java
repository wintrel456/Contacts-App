package com.gmail.l2t45s7e9.empty;

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

public class ContactListFragment extends ListFragment {
    private Contact contact1 = new Contact(
            "Debil",
            "+795131351",
            "+7561516",
            "email1",
            "email2",
            "Улица подзалупово дом 112",
            0);
    private Contact contact2 = new Contact(
            "Eblan",
            "+795641516",
            "+76161568",
            "email1",
            "email2",
            "Улица говна дом 0",
            0);

    private Contact[] contacts = new Contact[]{contact1, contact2};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_list_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView count = view.findViewById(R.id.contactCount);
        count.setText(String.valueOf(contacts.length));
        ContactListAdapter adapter = new ContactListAdapter(getActivity(), R.layout.element_of_contact_list, contacts);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        openDetails(position);
    }

    public void openDetails(int position) {
        ContactDetailsFragment contactDetailsFragment = new ContactDetailsFragment();
        Bundle id = new Bundle();
        id.putParcelable("listItem", contacts[position]);
        contactDetailsFragment.setArguments(id);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, contactDetailsFragment)
                .addToBackStack(null)
                .commit();
    }
}
