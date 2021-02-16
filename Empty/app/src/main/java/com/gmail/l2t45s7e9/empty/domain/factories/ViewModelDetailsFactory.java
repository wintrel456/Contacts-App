package com.gmail.l2t45s7e9.empty.domain.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Provider;

public class ViewModelDetailsFactory implements ViewModelProvider.Factory {

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;
    private String id;
    private int color;

    @Inject
    public ViewModelDetailsFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators, String id, int color) {
        this.creators = creators;
        this.id = id;
        this.color = color;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) creators.get(modelClass).get();
    }
}
