package com.gmail.l2t45s7e9.library.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.gmail.l2t45s7e9.java.interactor.AddressSearchInteractor;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;
public class AddressSerachViewModel extends ViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();
    private AddressSearchInteractor addressSearchInteractor;
    private MutableLiveData<List<String>> routeMutableLiveData = new MutableLiveData<>();
    public LiveData<List<String>> listLiveData;

    @Inject
    public AddressSerachViewModel(AddressSearchInteractor addressSearchInteractor) {
        this.addressSearchInteractor = addressSearchInteractor;
        listLiveData = routeMutableLiveData;
        getStartList("");
    }

    public void addAddressForContact(String address, String id) {
        Completable.fromAction(() -> addressSearchInteractor.setAddress(address, id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void getStartList(String filter) {
        disposable.add(
                addressSearchInteractor.getSearchList(filter)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(strings -> routeMutableLiveData.setValue(strings))
        );
    }


    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }


}
