package com.gmail.l2t45s7e9.empty.di.AddressSearch

import com.gmail.l2t45s7e9.empty.di.scopes.AddressSearchFragmentScope
import com.gmail.l2t45s7e9.library.interfaces.AddressSearchContainer
import dagger.Subcomponent

@AddressSearchFragmentScope
@Subcomponent(modules = [AddressSearchModule::class, AddressSearchRepositoryModule::class])
interface AddressSearchComponent : AddressSearchContainer