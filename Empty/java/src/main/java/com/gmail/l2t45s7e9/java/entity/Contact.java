package com.gmail.l2t45s7e9.java.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.util.GregorianCalendar;

@Entity
public class Contact {

    @NonNull
    @PrimaryKey
    private final String id;
    @NonNull
    @Ignore
    private final String name;
    @Nullable
    @Ignore
    private final String firstNumber;
    @Nullable
    @Ignore
    private final String secondNumber;
    @Nullable
    @Ignore
    private final String firstEmail;
    @Nullable
    @Ignore
    private final String secondEmail;
    @Nullable
    @ColumnInfo(name = "contactAddress")
    private final String contactAddress;
    @Ignore
    private final int contactColor;
    @Nullable
    @Ignore
    private final GregorianCalendar birthDate;

    @ColumnInfo(name = "latitude")
    private final double latitude;

    @ColumnInfo(name = "longitude")
    private final double longitude;


    public Contact(@NonNull String id, @Nullable String contactAddress, double latitude, double longitude) {
        this.id = id;
        this.contactAddress = contactAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.firstNumber = null;
        this.name = null;
        this.secondNumber = null;
        this.firstEmail = null;
        this.secondEmail = null;
        this.contactColor = 0;
        this.birthDate = null;
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
    @Ignore
    public String getName() {
        return name;
    }

    @Nullable
    @Ignore
    public String getFirstNumber() {
        return firstNumber;
    }

    @Nullable
    @Ignore
    public String getSecondNumber() {
        return secondNumber;
    }

    @Nullable
    @Ignore
    public String getFirstEmail() {
        return firstEmail;
    }

    @Nullable
    @Ignore
    public String getSecondEmail() {
        return secondEmail;
    }

    @Nullable
    public String getContactAddress() {
        return contactAddress;
    }

    @Ignore
    public int getContactColor() {
        return contactColor;
    }

    @Nullable
    @Ignore
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
