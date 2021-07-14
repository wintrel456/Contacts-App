package com.gmail.l2t45s7e9.empty.di.AddressSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.l2t45s7e9.library.domain.AddressSerachViewModel
import com.gmail.l2t45s7e9.library.domain.factories.ViewModelAddressSearchFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AddressSearchModule {
    @Binds
    @IntoMap
    @AddressSearchViewModelKey(AddressSerachViewModel::class)
    abstract fun bindAddressSearchViewModel(addressSearchViewModel: AddressSerachViewModel): ViewModel

    @Binds
    abstract fun bindAddressSearchViewModelFactory(viewModelAddressSearchFactory: ViewModelAddressSearchFactory): ViewModelProvider.Factory
}