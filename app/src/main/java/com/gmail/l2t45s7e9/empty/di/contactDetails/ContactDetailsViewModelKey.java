package com.gmail.l2t45s7e9.empty.di.contactDetails;

import androidx.lifecycle.ViewModel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@MapKey
public @interface ContactDetailsViewModelKey {
    Class<? extends ViewModel> value();
}
