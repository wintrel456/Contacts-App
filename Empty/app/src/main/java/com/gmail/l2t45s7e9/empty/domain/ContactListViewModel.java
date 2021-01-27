package com.gmail.l2t45s7e9.empty.domain;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.gmail.l2t45s7e9.empty.entity.Contact;
import com.gmail.l2t45s7e9.empty.presentation.adapter.ContactListAdapter;
import com.gmail.l2t45s7e9.empty.repository.ContactListRepository;
import java.util.List;

public class ContactListViewModel extends AndroidViewModel /*implements Filterable*/ {


    /*@Override
    public Filter getFilter() {

        return new Filter() {

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                contacts.clear();
                contacts.addAll((List<Contact>) results.values);
                contactListAdapter.notifyDataSetChanged();
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
    }*/

    public interface ShortInformation {
        void getContactList(List<Contact> result);
    }

    private ContactListRepository contactListRepository;
    private MutableLiveData<List<Contact>> contactListMutableLiveData = new MutableLiveData<>();
    public LiveData<List<Contact>> listLiveData;
    private ContactListAdapter contactListAdapter;
    private List<Contact> contacts;
    private List<Contact> contactsFull;
    private String filterPattern;

    /*public void setAdapter(ContactListAdapter adapter, List<Contact> contacts) {
        contactListAdapter = adapter;
        this.contacts = contacts;
        contactsFull = new ArrayList<>(contacts);
    }*/
    public void setFilterPattern(String filterPattern) {
        this.filterPattern = filterPattern;
        listLiveData = loadContactList();
    }

    public ContactListViewModel(@NonNull Application application) {
        super(application);
        contactListRepository = new ContactListRepository(application.getContentResolver(), application.getApplicationContext());
        listLiveData = loadContactList();
    }

    private LiveData<List<Contact>> loadContactList() {
        ShortInformation callback = new ShortInformation() {
            @Override
            public void getContactList(List<Contact> result) {
                contactListMutableLiveData.postValue(result);
            }
        };
        contactListRepository.getShortInformation(callback, filterPattern);
        return contactListMutableLiveData;
    }

}
