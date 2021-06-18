package com.gmail.l2t45s7e9.library.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.gmail.l2t45s7e9.java.entity.Contact;
import com.gmail.l2t45s7e9.java.interactor.ContactListInteractor;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;

public class ContactListViewModel extends ViewModel {

    public LiveData<List<Contact>> listLiveData;
    private ContactListInteractor contactListInteractor;
    private MutableLiveData<List<Contact>> contactListMutableLiveData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public ContactListViewModel(ContactListInteractor contactListInteractor) {
        this.contactListInteractor = contactListInteractor;
        listLiveData = contactListMutableLiveData;
        loadContactList("");
    }

    public void loadContactList(String filterPattern) {
        disposable.add(
                contactListInteractor.getContactListRepo(filterPattern)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(contacts -> contactListMutableLiveData.setValue(contacts))
        );
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

}
