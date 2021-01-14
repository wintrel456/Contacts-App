package com.gmail.l2t45s7e9.empty.domain.factories;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.gmail.l2t45s7e9.empty.domain.ContactDetailsViewModel;

public class ViewModelDetailsFactory extends ViewModelProvider.AndroidViewModelFactory {

    private final ContactDetailsViewModel contactDetailsViewModel;

    public ViewModelDetailsFactory(@NonNull Application application, String id, int color) {
        super(application);
        contactDetailsViewModel = new ContactDetailsViewModel(application, id, color);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) contactDetailsViewModel;
    }
}
