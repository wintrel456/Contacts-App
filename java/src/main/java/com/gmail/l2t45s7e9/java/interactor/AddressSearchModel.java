package com.gmail.l2t45s7e9.java.interactor;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class AddressSearchModel implements AddressSearchInteractor {

    private AddressSearchRepository addressSearchRepository;

    public AddressSearchModel(AddressSearchRepository addressSearchRepository) {
        this.addressSearchRepository = addressSearchRepository;
    }

    @Override
    public Single<List<String>> getSearchList(String filter) {
        return addressSearchRepository.loadSearchList(filter);
    }

    @Override
    public void setAddress(String address, String id) {
        addressSearchRepository.insertAddressIntoDB(address, id);
    }
}
