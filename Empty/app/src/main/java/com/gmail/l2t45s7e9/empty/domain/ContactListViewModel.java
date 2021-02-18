package com.gmail.l2t45s7e9.empty.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.gmail.l2t45s7e9.empty.di.ContactList.ListRepositoryGetter;
import com.gmail.l2t45s7e9.empty.entity.Contact;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;

public class ContactListViewModel extends ViewModel {

    private ListRepositoryGetter listRepositoryGetter;
    private MutableLiveData<List<Contact>> contactListMutableLiveData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    public LiveData<List<Contact>> listLiveData;

    @Inject
    public ContactListViewModel(ListRepositoryGetter listRepositoryGetter) {
        this.listRepositoryGetter = listRepositoryGetter;
        listLiveData = contactListMutableLiveData;
        loadContactList("");
    }

    public void loadContactList(String filterPattern) {
        disposable.add(
                Single.fromCallable(() -> listRepositoryGetter
                        .getListRepository()
                        .loadShortInformation(filterPattern))
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
