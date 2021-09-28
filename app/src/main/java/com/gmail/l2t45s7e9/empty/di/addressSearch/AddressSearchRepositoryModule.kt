package com.gmail.l2t45s7e9.empty.di.addressSearch

import android.location.Geocoder
import com.gmail.l2t45s7e9.empty.di.scopes.AddressSearchFragmentScope
import com.gmail.l2t45s7e9.java.interactor.AddressSearchInteractor
import com.gmail.l2t45s7e9.java.interactor.AddressSearchModel
import com.gmail.l2t45s7e9.java.interactor.AddressSearchRepository
import com.gmail.l2t45s7e9.library.dataBase.ContactAddressDataBase
import com.gmail.l2t45s7e9.library.repository.AddressSearchRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class AddressSearchRepositoryModule {
    @Provides
    @AddressSearchFragmentScope
    fun provideAddressSearchRepository(
        contactAddressDataBase: ContactAddressDataBase,
        geocoder: Geocoder
    ): AddressSearchRepository {
        return AddressSearchRepositoryImpl(contactAddressDataBase, geocoder)
    }

    @Provides
    @AddressSearchFragmentScope
    fun provideAddressSearchInteractor(addressSearchRepository: AddressSearchRepository): AddressSearchInteractor {
        return AddressSearchModel(addressSearchRepository)
    }
}
