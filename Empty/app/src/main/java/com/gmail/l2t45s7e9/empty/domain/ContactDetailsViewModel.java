package com.gmail.l2t45s7e9.empty.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.gmail.l2t45s7e9.empty.entity.Contact;
import com.gmail.l2t45s7e9.empty.repository.ContactDetailsRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javax.inject.Inject;

public class ContactDetailsViewModel extends ViewModel {

    private final ContactDetailsRepository contactDetailsRepository;
    private MutableLiveData<Contact> contactDetailsMutableLiveData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private String id;
    private int color;
    public LiveData<Contact> contactLiveData;

    public void setIdAndColor(String id, int color) {
        this.id = id;
        this.color = color;
    }

    @Inject
    public ContactDetailsViewModel(ContactDetailsRepository contactDetailsRepository) {
        this.contactDetailsRepository = contactDetailsRepository;
        //contactLiveData = loadContactDetails();
    }

    public LiveData<Contact> loadContactDetails(String id, int color) {
        disposable.add(
                Single.fromCallable(() -> contactDetailsRepository.loadDetailsInformation(id, color))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(contacts -> contactDetailsMutableLiveData.setValue(contacts))
        );
        return contactDetailsMutableLiveData;
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }
}
