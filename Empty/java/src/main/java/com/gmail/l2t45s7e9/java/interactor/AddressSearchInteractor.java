package com.gmail.l2t45s7e9.java.interactor;

import io.reactivex.rxjava3.core.Single;
import java.util.List;
public interface AddressSearchInteractor {
    Single<List<String>> getSearchList(String filter);

    void setAddress(String address, String id);
}
