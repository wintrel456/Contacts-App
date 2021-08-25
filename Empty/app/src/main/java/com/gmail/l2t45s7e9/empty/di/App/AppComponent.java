package com.gmail.l2t45s7e9.empty.di.App;

import com.gmail.l2t45s7e9.empty.di.AddressSearch.AddressSearchComponent;
import com.gmail.l2t45s7e9.empty.di.ContactDetails.ContactDetailsComponent;
import com.gmail.l2t45s7e9.empty.di.ContactList.ContactListComponent;
import com.gmail.l2t45s7e9.empty.di.Map.MapComponent;
import com.gmail.l2t45s7e9.empty.di.Schedulers.DispatchersProviderModule;
import com.gmail.l2t45s7e9.empty.di.Schedulers.SchedulersProviderModule;
import com.gmail.l2t45s7e9.empty.di.database.DataBaseProviderModule;
import com.gmail.l2t45s7e9.empty.di.geocoder.GeocoderProviderModule;
import com.gmail.l2t45s7e9.empty.di.response.ResponseModule;
import com.gmail.l2t45s7e9.library.interfaces.AppContainer;
import dagger.Component;
import javax.inject.Singleton;

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
