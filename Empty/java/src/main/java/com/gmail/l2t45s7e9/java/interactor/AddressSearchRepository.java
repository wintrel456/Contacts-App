package com.gmail.l2t45s7e9.java.interactor;

import io.reactivex.rxjava3.core.Single;
import java.util.List;
public interface AddressSearchRepository {
    Single<List<String>> loadSearchList(String filter);

    void insert(String address, String id);
}
