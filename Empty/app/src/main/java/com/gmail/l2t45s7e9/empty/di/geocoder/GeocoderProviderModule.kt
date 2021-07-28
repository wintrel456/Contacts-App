package com.gmail.l2t45s7e9.empty.di.geocoder

import android.content.Context
import android.location.Geocoder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GeocoderProviderModule {
    @Provides
    @Singleton
    fun provideGeocoder(context: Context): Geocoder {
        return Geocoder(context)
    }
}