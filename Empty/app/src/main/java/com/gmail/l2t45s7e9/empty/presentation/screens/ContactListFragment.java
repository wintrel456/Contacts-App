package com.gmail.l2t45s7e9.empty.presentation.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gmail.l2t45s7e9.empty.R;
import com.gmail.l2t45s7e9.empty.domain.ContactListViewModel;
import com.gmail.l2t45s7e9.empty.domain.factories.ViewModelListFactory;
import com.gmail.l2t45s7e9.empty.entity.Contact;
import com.gmail.l2t45s7e9.empty.presentation.adapter.ContactListAdapter;
import java.util.ArrayList;
import java.util.List;

public class ContactListFragment extends Fragment {

    private List<Contact> contacts = new ArrayList<>();
    private ContactListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView count = view.findViewById(R.id.contactCount);
        adapter = new ContactListAdapter();
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
                        adapter.setContacts(contacts);
                        count.setText(String.valueOf(contacts.size()));
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });

        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        adapter = null;
        super.onDestroyView();
    }
}
