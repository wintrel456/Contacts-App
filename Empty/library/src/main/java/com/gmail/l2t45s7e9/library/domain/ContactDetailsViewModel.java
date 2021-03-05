package com.gmail.l2t45s7e9.library.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.gmail.l2t45s7e9.java.entity.Contact;
import com.gmail.l2t45s7e9.java.interactor.ContactDetailsInteractor;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javax.inject.Inject;

public class ContactDetailsViewModel extends ViewModel {

    private ContactDetailsInteractor contactDetailsInteractor;
    private MutableLiveData<Contact> contactDetailsMutableLiveData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public ContactDetailsViewModel(ContactDetailsInteractor contactDetailsInteractor) {
        this.contactDetailsInteractor = contactDetailsInteractor;
    }

    public LiveData<Contact> loadContactDetails(String id, int color) {
        disposable.add(
                contactDetailsInteractor.getContactDetailsRepo(id, color)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(contactDetailsInfo -> contactDetailsMutableLiveData.setValue(contactDetailsInfo))
        );
        return contactDetailsMutableLiveData;
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

}
