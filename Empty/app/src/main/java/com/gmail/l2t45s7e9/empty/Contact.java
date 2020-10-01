package com.gmail.l2t45s7e9.empty;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
    private String name;
    private String firstNumber;
    private String secondNumber;
    private String firstEmail;
    private String secondEmail;
    private String contactAddress;

    public Contact(String name, String firstNumber, String secondNumber, String firstEmail, String secondEmail, String contactAddress) {
        this.name = name;
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.firstEmail = firstEmail;
        this.secondEmail = secondEmail;
        this.contactAddress = contactAddress;
    }

    protected Contact(Parcel in) {
        name = in.readString();
        firstNumber = in.readString();
        secondNumber = in.readString();
        firstEmail = in.readString();
        secondEmail = in.readString();
        contactAddress = in.readString();
    }

    public String getName() {
        return name;
    }

    public String getFirstNumber() {
        return firstNumber;
    }

    public String getSecondNumber() {
        return secondNumber;
    }

    public String getFirstEmail() {
        return firstEmail;
    }

    public String getSecondEmail() {
        return secondEmail;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{name, firstNumber, secondNumber, firstEmail, secondEmail});
    }

}
