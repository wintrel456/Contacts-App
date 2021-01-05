package com.gmail.l2t45s7e9.empty.domain.factories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

public class ListFactory extends ViewModelProvider.AndroidViewModelFactory {

    public ListFactory(@NonNull Application application) {
        super(application);
    }
}
