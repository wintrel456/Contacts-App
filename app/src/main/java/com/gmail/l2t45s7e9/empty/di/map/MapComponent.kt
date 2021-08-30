package com.gmail.l2t45s7e9.empty.di.map

import com.gmail.l2t45s7e9.empty.di.scopes.MapFragmentScope
import com.gmail.l2t45s7e9.library.interfaces.MapContainer
import dagger.Subcomponent

@MapFragmentScope
@Subcomponent(modules = [MapModule::class, MapRepositoryModule::class])
interface MapComponent : MapContainer
