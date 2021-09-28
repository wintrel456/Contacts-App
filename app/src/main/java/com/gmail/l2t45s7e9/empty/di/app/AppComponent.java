package com.gmail.l2t45s7e9.empty.di.app;

import com.gmail.l2t45s7e9.empty.di.addressSearch.AddressSearchComponent;
import com.gmail.l2t45s7e9.empty.di.contactDetails.ContactDetailsComponent;
import com.gmail.l2t45s7e9.empty.di.contactList.ContactListComponent;
import com.gmail.l2t45s7e9.empty.di.map.MapComponent;
import com.gmail.l2t45s7e9.empty.di.schedulers.DispatchersProviderModule;
import com.gmail.l2t45s7e9.empty.di.schedulers.SchedulersProviderModule;
import com.gmail.l2t45s7e9.empty.di.database.DataBaseProviderModule;
import com.gmail.l2t45s7e9.empty.di.geocoder.GeocoderProviderModule;
import com.gmail.l2t45s7e9.empty.di.response.ResponseModule;
import com.gmail.l2t45s7e9.library.interfaces.AppContainer;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        SchedulersProviderModule.class,
        DataBaseProviderModule.class,
        GeocoderProviderModule.class,
        ResponseModule.class,
        DispatchersProviderModule.class
}
)
public interface AppComponent extends AppContainer {
    ContactDetailsComponent plusContactDetailsContainer();

    ContactListComponent plusContactListContainer();

    AddressSearchComponent plusAddressSearchContainer();

    MapComponent plusMapContainer();
}
