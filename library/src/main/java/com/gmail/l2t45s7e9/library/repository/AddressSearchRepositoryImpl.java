package com.gmail.l2t45s7e9.library.repository;

import android.location.Address;
import android.location.Geocoder;

import com.gmail.l2t45s7e9.java.interactor.AddressSearchRepository;
import com.gmail.l2t45s7e9.library.dataBase.ContactAddressDataBase;
import com.gmail.l2t45s7e9.library.dataBase.ContactData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class AddressSearchRepositoryImpl implements AddressSearchRepository {

    private Geocoder geocoder;
    private ContactAddressDataBase db;

    public AddressSearchRepositoryImpl(
            ContactAddressDataBase contactAddressDataBase,
            Geocoder geocoder
    ) {
        this.geocoder = geocoder;
        this.db = contactAddressDataBase;
    }

    @Override
    public Single<List<String>> loadSearchList(String filter) {
        List<String> strings = new ArrayList<>();
        List<Address> addresses = new ArrayList<>();
        if (filter.length() != 0) {
            try {
                addresses = geocoder.getFromLocationName(filter, 1);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            if (addresses.size() != 0) {
                strings.add(addresses.get(0).getAddressLine(0));
            }
        }
        return Single.fromCallable(() -> strings);
    }

    @Override
    public void insertAddressIntoDB(String address, String id) {
        List<Address> latLng = null;
        try {
            latLng = geocoder.getFromLocationName(address, 1);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (latLng != null) {
                db.contactDao().insert(new ContactData(
                        id,
                        address,
                        latLng.get(0).getLatitude(),
                        latLng.get(0).getLongitude())
                );
            }
        }
    }


}
