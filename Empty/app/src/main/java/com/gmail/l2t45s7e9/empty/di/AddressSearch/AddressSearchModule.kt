package com.gmail.l2t45s7e9.empty.di.AddressSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.l2t45s7e9.library.domain.AddressSearchViewModel
import com.gmail.l2t45s7e9.library.domain.factories.ViewModelAddressSearchFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AddressSearchModule {
    @Binds
    @IntoMap
    @AddressSearchViewModelKey(AddressSearchViewModel::class)
    abstract fun bindAddressSearchViewModel(
            addressSearchViewModel: AddressSearchViewModel
    ): ViewModel

    @Binds
    abstract fun bindAddressSearchViewModelFactory(
            viewModelAddressSearchFactory: ViewModelAddressSearchFactory
    ): ViewModelProvider.Factory
}