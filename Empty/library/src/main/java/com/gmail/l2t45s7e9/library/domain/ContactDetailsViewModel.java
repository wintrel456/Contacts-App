package com.gmail.l2t45s7e9.library.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.gmail.l2t45s7e9.java.entity.Contact;
import com.gmail.l2t45s7e9.java.interactor.ContactDetailsInteractor;
import com.gmail.l2t45s7e9.java.interactor.NotificationInteractor;
import com.gmail.l2t45s7e9.library.interfaces.SchedulersProvider;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import javax.inject.Inject;

public class ContactDetailsViewModel extends ViewModel {

    private ContactDetailsInteractor contactDetailsInteractor;
    private SchedulersProvider schedulersProvider;
    private NotificationInteractor notificationInteractor;
    private MutableLiveData<Contact> contactDetailsMutableLiveData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public ContactDetailsViewModel(
            ContactDetailsInteractor contactDetailsInteractor,
            NotificationInteractor notificationInteractor,
            SchedulersProvider schedulersProvider
    ) {
        this.contactDetailsInteractor = contactDetailsInteractor;
        this.notificationInteractor = notificationInteractor;
        this.schedulersProvider = schedulersProvider;
    }

    public LiveData<Contact> loadContactDetails(String id, int color) {
        disposable.add(
                contactDetailsInteractor.getContactDetailsRepo(id, color)
                        .subscribeOn(schedulersProvider.io())
                        .observeOn(schedulersProvider.ui())
                        .subscribe(contactDetailsInfo -> contactDetailsMutableLiveData.setValue(contactDetailsInfo))
        );
        return contactDetailsMutableLiveData;
    }

    public void setNotification() {
        notificationInteractor.setNotification(contactDetailsMutableLiveData.getValue());
    }

    public void cancelNotification() {
        notificationInteractor.cancelNotification(contactDetailsMutableLiveData.getValue());
    }

    public boolean getStatus() {
        return notificationInteractor.status(contactDetailsMutableLiveData.getValue());
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

}
