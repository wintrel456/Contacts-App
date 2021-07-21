package com.gmail.l2t45s7e9.library.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.gmail.l2t45s7e9.java.entity.Contact;
import com.gmail.l2t45s7e9.java.interactor.ContactListInteractor;
import com.gmail.l2t45s7e9.library.interfaces.SchedulersProvider;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import java.util.List;
import javax.inject.Inject;

public class ContactListViewModel extends ViewModel {


    private ContactListInteractor contactListInteractor;
    private MutableLiveData<List<Contact>> contactListMutableLiveData = new MutableLiveData<>();
    private SchedulersProvider schedulersProvider;
    private CompositeDisposable disposable = new CompositeDisposable();
    public LiveData<List<Contact>> listLiveData = contactListMutableLiveData;

    @Inject
    public ContactListViewModel(
            ContactListInteractor contactListInteractor,
            SchedulersProvider schedulersProvider
    ) {
        this.contactListInteractor = contactListInteractor;
        this.schedulersProvider = schedulersProvider;
    }

    public void loadContactList(String filterPattern) {
        disposable.add(
                contactListInteractor.getContactListRepo(filterPattern)
                        .subscribeOn(schedulersProvider.io())
                        .observeOn(schedulersProvider.ui())
                        .subscribe(contacts -> contactListMutableLiveData.setValue(contacts))
        );
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

}
