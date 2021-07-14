package com.gmail.l2t45s7e9.library.repository;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import androidx.room.Room;
import com.gmail.l2t45s7e9.java.entity.Contact;
import com.gmail.l2t45s7e9.java.interactor.AddressSearchRepository;
import com.gmail.l2t45s7e9.library.dataBase.ContactAddressDataBase;
import io.reactivex.rxjava3.core.Single;
import java.util.ArrayList;
import java.util.List;
public class AddressSearchRepositoryImpl implements AddressSearchRepository {

    private Geocoder geocoder;
    private ContactAddressDataBase db;

    public AddressSearchRepositoryImpl(Context context) {
        geocoder = new Geocoder(context);
        db = Room.databaseBuilder(context,
                ContactAddressDataBase.class, "contact-address2").build();
    }

    @Override
    public Single<List<String>> loadSearchList(String filter) {
        List<String> strings = new ArrayList<>();
        List<Address> addresses = new ArrayList<>();
        if (filter.length() != 0) {
            try {
                addresses = geocoder.getFromLocationName(filter, 20);
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
    public void insert(String address, String id) {
        List<Address> latLng = null;
        try {
            latLng = geocoder.getFromLocationName(address, 1);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (latLng != null) {
                db.contactDao().insert(new Contact(id, address, latLng.get(0).getLatitude(), latLng.get(0).getLongitude()));
            }
        }
    }


}
