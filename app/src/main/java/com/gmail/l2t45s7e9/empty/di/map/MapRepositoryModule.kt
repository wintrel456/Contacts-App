package com.gmail.l2t45s7e9.empty.di.map

import android.content.Context
import com.gmail.l2t45s7e9.empty.di.scopes.MapFragmentScope
import com.gmail.l2t45s7e9.java.interactor.MapInteractor
import com.gmail.l2t45s7e9.java.interactor.MapModel
import com.gmail.l2t45s7e9.java.interactor.MapRepository
import com.gmail.l2t45s7e9.library.dataBase.ContactAddressDataBase
import com.gmail.l2t45s7e9.library.pojo.RouteApi
import com.gmail.l2t45s7e9.library.repository.MapRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class MapRepositoryModule {
    @Provides
    @MapFragmentScope
    fun provideMapRepository(
        contactAddressDataBase: ContactAddressDataBase,
        context: Context,
        routeApi: RouteApi
    ): MapRepository {
        return MapRepositoryImpl(contactAddressDataBase, context, routeApi)
    }

    @Provides
    @MapFragmentScope
    fun provideMapInteractor(mapRepository: MapRepository): MapInteractor {
        return MapModel(mapRepository)
    }
}
