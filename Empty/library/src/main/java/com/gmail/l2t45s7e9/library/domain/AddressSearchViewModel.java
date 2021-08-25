package com.gmail.l2t45s7e9.library.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.gmail.l2t45s7e9.java.interactor.AddressSearchInteractor;
import com.gmail.l2t45s7e9.library.interfaces.SchedulersProvider;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
public class AddressSearchViewModel extends ViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();
    private AddressSearchInteractor addressSearchInteractor;
    private MutableLiveData<List<String>> routeMutableLiveData = new MutableLiveData<>();
    public LiveData<List<String>> listLiveData = routeMutableLiveData;

    private SchedulersProvider schedulersProvider;

    @Inject
    public AddressSearchViewModel(
            AddressSearchInteractor addressSearchInteractor,
            SchedulersProvider schedulersProvider) {
        this.addressSearchInteractor = addressSearchInteractor;
        this.schedulersProvider = schedulersProvider;
        getStartList("");
    }

    public void addAddressForContact(String address, String id) {
        disposable.add(
                Completable.fromAction(() -> addressSearchInteractor.setAddress(address, id))
                        .subscribeOn(schedulersProvider.io())
                        .subscribe()
        );
    }

    private void getStartList(String filter) {
        disposable.add(
                addressSearchInteractor.getSearchList(filter)
                        .subscribeOn(schedulersProvider.io())
                        .observeOn(schedulersProvider.ui())
                        .subscribe(strings -> routeMutableLiveData.setValue(strings))
        );
    }

    public void textFilter(String filter) {
        disposable.add(
                Observable.create((ObservableOnSubscribe<String>) emitter ->
                        emitter.onNext(filter))
                        .map((Function<String, String>) text ->
                                text.toLowerCase().trim())
                        .debounce(250, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .subscribe((Consumer<String>) this::getStartList)
        );
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }


}
