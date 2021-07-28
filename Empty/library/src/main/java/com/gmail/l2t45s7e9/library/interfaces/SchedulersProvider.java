package com.gmail.l2t45s7e9.library.interfaces;

import io.reactivex.rxjava3.core.Scheduler;
public interface SchedulersProvider {
    Scheduler io();

    Scheduler computation();

    Scheduler ui();
}
