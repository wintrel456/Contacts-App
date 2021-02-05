package com.gmail.l2t45s7e9.empty.domain;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.gmail.l2t45s7e9.empty.entity.Contact;
import com.gmail.l2t45s7e9.empty.repository.ContactDetailsRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ContactDetailsViewModel extends AndroidViewModel {

    private final ContactDetailsRepository contactDetailsRepository;
    private MutableLiveData<Contact> contactDetailsMutableLiveData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    public LiveData<Contact> contactLiveData;
    private String id;
    private int color;

    public ContactDetailsViewModel(@NonNull Application application, String id, int color) {
        super(application);
        contactDetailsRepository = new ContactDetailsRepository(application.getContentResolver());
        this.id = id;
        this.color = color;
        contactLiveData = loadContactDetails();
    }

    private LiveData<Contact> loadContactDetails() {
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
