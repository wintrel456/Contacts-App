package com.gmail.l2t45s7e9.java.interactor;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateModel implements CurrentDate {
    @Override
    public GregorianCalendar getCurrentDate() {
        return (GregorianCalendar) Calendar.getInstance();
    }

}
