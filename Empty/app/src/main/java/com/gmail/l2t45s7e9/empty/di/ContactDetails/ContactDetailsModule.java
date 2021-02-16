package com.gmail.l2t45s7e9.empty.di.ContactDetails;

import androidx.lifecycle.ViewModel;
import com.gmail.l2t45s7e9.empty.domain.factories.ViewModelDetailsFactory;
import dagger.Module;
import dagger.Provides;
import java.util.Map;
import javax.inject.Provider;
@Module
public class ContactDetailsModule {

    /*@Binds
    @IntoMap
    @ContactDetailsViewModelKey(ContactDetailsViewModel.class)
    abstract ViewModel bindContactDetailsViewModel(ContactDetailsViewModel contactDetailsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelDetailsFactory viewModelDetailsFactory);*/
    @Provides
    ViewModelDetailsFactory provideDetailsFactory(
            Map<Class<? extends ViewModel>, Provider<ViewModel>> creators,
            String id,
            int color
    ) {
        return new ViewModelDetailsFactory(creators, id, color);
    }

}
