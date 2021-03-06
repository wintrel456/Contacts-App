package com.gmail.l2t45s7e9.library.presentation.screens;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gmail.l2t45s7e9.library.R;
import com.gmail.l2t45s7e9.library.domain.ContactListViewModel;
import com.gmail.l2t45s7e9.library.domain.factories.ViewModelListFactory;
import com.gmail.l2t45s7e9.library.interfaces.ContactListContainer;
import com.gmail.l2t45s7e9.library.interfaces.HasAppContainer;
import com.gmail.l2t45s7e9.library.presentation.adapter.ContactItemDecorator;
import com.gmail.l2t45s7e9.library.presentation.adapter.ContactListAdapter;
import javax.inject.Inject;


public class ContactListFragment extends Fragment {

    @Inject
    ViewModelListFactory viewModelListFactory;
    private RecyclerView recyclerView;
    private ContactListAdapter adapter;
    private ContactListViewModel contactListViewModel;
    private ProgressBar progressBar;


    private ContactListAdapter.OnItemClickListener onItemClickListener = (contact, view) -> {
        String id = contact.getId();
        int color = contact.getContactColor();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putInt("color", color);
        Navigation.findNavController(view).navigate(R.id.action_contactListFragment_to_contactDetailsFragment, bundle);
    };


    @Override
    public void onAttach(@NonNull Context context) {
        Application app = requireActivity().getApplication();
        if (!(app instanceof HasAppContainer)) {
            throw new IllegalStateException();
        }
        ContactListContainer contactListContainer = ((HasAppContainer) app).appContainer()
                .plusContactListContainer();
        contactListContainer.inject(this);
        super.onAttach(context);
    }

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
        progressBar = view.findViewById(R.id.progressBar);
        adapter = new ContactListAdapter(onItemClickListener);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(contactItemDecorator);

        contactListViewModel = new ViewModelProvider(
                this,
                viewModelListFactory
        ).get(ContactListViewModel.class);

        contactListViewModel.listLiveData.observe(getViewLifecycleOwner(), result -> {
            if (adapter != null) {
                adapter.submitList(result);
                count.setText(String.valueOf(result.size()));
                progressBar.setVisibility(View.GONE);
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
                progressBar.setVisibility(View.VISIBLE);
                contactListViewModel.loadContactList(filterPattern);
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        recyclerView.setAdapter(null);
        recyclerView.setLayoutManager(null);
        progressBar = null;
        super.onDestroyView();
    }

}
