package com.gmail.l2t45s7e9.empty.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.gmail.l2t45s7e9.empty.di.ContactDetails.DetailsRepositoryGetter;
import com.gmail.l2t45s7e9.empty.entity.Contact;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javax.inject.Inject;

public class ContactDetailsViewModel extends ViewModel {

    private DetailsRepositoryGetter detailsRepositoryGetter;
    private MutableLiveData<Contact> contactDetailsMutableLiveData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public ContactDetailsViewModel(DetailsRepositoryGetter detailsRepositoryGetter) {
        this.detailsRepositoryGetter = detailsRepositoryGetter;
    }

    public LiveData<Contact> loadContactDetails(String id, int color) {
        disposable.add(
                Single.fromCallable(() -> detailsRepositoryGetter
                        .getDetailsRepository()
                        .loadDetailsInformation(id, color))
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
