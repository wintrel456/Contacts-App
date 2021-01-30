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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gmail.l2t45s7e9.empty.R;
import com.gmail.l2t45s7e9.empty.domain.ContactListViewModel;
import com.gmail.l2t45s7e9.empty.domain.factories.ViewModelListFactory;
import com.gmail.l2t45s7e9.empty.entity.Contact;
import com.gmail.l2t45s7e9.empty.presentation.adapter.ContactItemDecorator;
import com.gmail.l2t45s7e9.empty.presentation.adapter.ContactListAdapter;
import java.util.List;

public class ContactListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ContactListAdapter adapter;
    private ContactListViewModel contactListViewModel;
    private ContactListAdapter.OnItemClickListener onItemClickListener = new ContactListAdapter.OnItemClickListener() {
        @Override
        public void onItemClicked(Contact contact, View view) {
            String id = contact.getId();
            int color = contact.getContactColor();
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            bundle.putInt("color", color);
            Navigation.findNavController(view).navigate(R.id.action_contactListFragment_to_contactDetailsFragment, bundle);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ContactItemDecorator contactItemDecorator = new ContactItemDecorator(
                (int) (8 * getResources().getDisplayMetrics().density)
        );
        final TextView count = view.findViewById(R.id.contactCount);
        adapter = new ContactListAdapter(onItemClickListener);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(contactItemDecorator);
        contactListViewModel = new ViewModelProvider(
                this,
                new ViewModelListFactory(getActivity().getApplication())).get(ContactListViewModel.class
        );
        contactListViewModel.listLiveData.observe(getViewLifecycleOwner(), new Observer<List<Contact>>() {
            @Override
            public void onChanged(final List<Contact> result) {
                if (adapter != null) {
                    adapter.submitList(result);
                    count.setText(String.valueOf(result.size()));
                }
            }
        });
        recyclerView.setAdapter(adapter);

        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String filterPattern) {
                contactListViewModel.loadContactList(filterPattern);
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        recyclerView.setAdapter(null);
        recyclerView.setLayoutManager(null);
        super.onDestroyView();
    }

}
