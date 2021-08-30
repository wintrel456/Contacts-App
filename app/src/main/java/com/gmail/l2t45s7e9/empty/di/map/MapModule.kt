package com.gmail.l2t45s7e9.empty.di.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.l2t45s7e9.empty.di.scopes.MapFragmentScope
import com.gmail.l2t45s7e9.library.domain.MapViewModel
import com.gmail.l2t45s7e9.library.domain.factories.ViewModelMapFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MapModule {
    @Binds
    @IntoMap
    @MapFragmentScope
    @MapViewModelKey(MapViewModel::class)
    abstract fun bindMapViewModel(
        mapViewModel: MapViewModel
    ): ViewModel

    @MapFragmentScope
    @Binds
    abstract fun bindMapViewModelFactory(
        viewModelMapFactory: ViewModelMapFactory
    ): ViewModelProvider.Factory
}
