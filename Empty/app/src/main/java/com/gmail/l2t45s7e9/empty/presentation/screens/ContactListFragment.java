package com.gmail.l2t45s7e9.empty.presentation.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.gmail.l2t45s7e9.empty.R;
import com.gmail.l2t45s7e9.empty.domain.ContactListViewModel;
import com.gmail.l2t45s7e9.empty.domain.factories.ViewModelListFactory;
import com.gmail.l2t45s7e9.empty.entity.Contact;
import com.gmail.l2t45s7e9.empty.presentation.adapter.ContactListAdapter;
import java.util.ArrayList;
import java.util.List;

public class ContactListFragment extends ListFragment {

    private List<Contact> contacts = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_list_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView count = view.findViewById(R.id.contactCount);
        final ContactListAdapter adapter = new ContactListAdapter(getContext(), contacts);
        ContactListViewModel contactListViewModel = new ViewModelProvider(
                this,
                new ViewModelListFactory(getActivity().getApplication())).get(ContactListViewModel.class
        );
        contactListViewModel.listLiveData.observe(getViewLifecycleOwner(), new Observer<List<Contact>>() {
            @Override
            public void onChanged(final List<Contact> result) {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        contacts = result;
                        count.setText(String.valueOf(contacts.size()));
                        adapter.updateData(contacts);
                        setListAdapter(adapter);
                    }
                });
            }
        });
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        openDetails(position);
    }

    public void openDetails(int position) {
        String id = contacts.get(position).getId();
        int color = contacts.get(position).getContactColor();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putInt("color", color);
        Navigation.findNavController(getView()).navigate(R.id.action_contactListFragment_to_contactDetailsFragment, bundle);
    }

}
