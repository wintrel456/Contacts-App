package com.gmail.l2t45s7e9.empty.domain;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.gmail.l2t45s7e9.empty.entity.Contact;
import com.gmail.l2t45s7e9.empty.repository.ContactListRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;

public class ContactListViewModel extends AndroidViewModel {

    private ContactListRepository contactListRepository;
    private MutableLiveData<List<Contact>> contactListMutableLiveData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    public LiveData<List<Contact>> listLiveData;

    public ContactListViewModel(@NonNull Application application) {
        super(application);
        contactListRepository = new ContactListRepository(
                application.getContentResolver(),
                application.getApplicationContext()
        );
        listLiveData = contactListMutableLiveData;
        loadContactList("");
    }

    public void loadContactList(String filterPattern) {
        disposable.add(
                Observable.fromCallable(() -> contactListRepository.loadShortInformation(filterPattern))
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
