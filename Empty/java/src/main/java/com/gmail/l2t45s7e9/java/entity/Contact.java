package com.gmail.l2t45s7e9.java.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.GregorianCalendar;


public class Contact {

    @NonNull
    private final String id;
    @NonNull
    private final String name;
    @Nullable
    private final String firstNumber;
    @Nullable
    private final String secondNumber;
    @Nullable
    private final String firstEmail;
    @Nullable
    private final String secondEmail;
    @Nullable
    private final String contactAddress;

    private final int contactColor;
    @Nullable
    private final GregorianCalendar birthDate;

    private final double latitude;

    private final double longitude;


    public Contact(@NonNull String id, @Nullable String contactAddress, double latitude, double longitude) {
        this.id = id;
        this.contactAddress = contactAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.secondNumber = null;
        this.firstEmail = null;
        this.secondEmail = null;
        this.contactColor = 0;
        this.birthDate = null;
        this.name = null;
        this.firstNumber = null;
    }

    public Contact(
            @NonNull String id,
            @NonNull String name,
            @Nullable String firstNumber,
            @Nullable String secondNumber,
            @Nullable String firstEmail,
            @Nullable String secondEmail,
            @Nullable String contactAddress,
            @Nullable GregorianCalendar birthDate,
            int contactColor
    ) {
        this.id = id;
        this.name = name;
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.firstEmail = firstEmail;
        this.secondEmail = secondEmail;
        this.contactAddress = contactAddress;
        this.contactColor = contactColor;
        this.birthDate = birthDate;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    public Contact(
            @NonNull String id,
            @NonNull String name,
            @Nullable String firstNumber,
            int contactColor
    ) {
        this.id = id;
        this.name = name;
        this.firstNumber = firstNumber;
        this.contactColor = contactColor;
        this.secondNumber = null;
        this.firstEmail = null;
        this.secondEmail = null;
        this.contactAddress = null;
        this.birthDate = null;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getFirstNumber() {
        return firstNumber;
    }

    @Nullable
    public String getSecondNumber() {
        return secondNumber;
    }

    @Nullable
    public String getFirstEmail() {
        return firstEmail;
    }

    @Nullable
    public String getSecondEmail() {
        return secondEmail;
    }

    @Nullable
    public String getContactAddress() {
        return contactAddress;
    }

    public int getContactColor() {
        return contactColor;
    }

    @Nullable
    public GregorianCalendar getBirthDate() {
        return birthDate;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


}
