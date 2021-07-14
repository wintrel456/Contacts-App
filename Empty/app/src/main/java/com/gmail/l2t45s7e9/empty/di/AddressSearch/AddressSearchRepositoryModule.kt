package com.gmail.l2t45s7e9.empty.di.AddressSearch

import android.content.Context
import com.gmail.l2t45s7e9.empty.di.scopes.AddressSearchFragmentScope
import com.gmail.l2t45s7e9.java.interactor.AddressSearchInteractor
import com.gmail.l2t45s7e9.java.interactor.AddressSearchModel
import com.gmail.l2t45s7e9.java.interactor.AddressSearchRepository
import com.gmail.l2t45s7e9.library.repository.AddressSearchRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class AddressSearchRepositoryModule {
    @Provides
    @AddressSearchFragmentScope
    fun provideAddressSearchRepository(context: Context): AddressSearchRepository {
        return AddressSearchRepositoryImpl(context)
    }

    @Provides
    @AddressSearchFragmentScope
    fun provideAddressSearchInteractor(addressSearchRepository: AddressSearchRepository): AddressSearchInteractor {
        return AddressSearchModel(addressSearchRepository)
    }
}