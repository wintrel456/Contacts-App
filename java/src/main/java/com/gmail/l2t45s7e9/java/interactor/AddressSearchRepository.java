package com.gmail.l2t45s7e9.java.interactor;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface AddressSearchRepository {
    Single<List<String>> loadSearchList(String filter);

    void insertAddressIntoDB(String address, String id);
}
