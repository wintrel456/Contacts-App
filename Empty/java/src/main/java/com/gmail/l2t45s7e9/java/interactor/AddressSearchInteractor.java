package com.gmail.l2t45s7e9.java.interactor;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface AddressSearchInteractor {
    Single<List<String>> getSearchList(String filter);

    void setAddress(String address, String id);
}
