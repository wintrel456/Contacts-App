package com.gmail.l2t45s7e9.empty;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Contact {

    private final String name;
    private final String firstNumber;
    private final String secondNumber;
    private final String firstEmail;
    private final String secondEmail;
    private final String contactAddress;
    private final int contactColor;
    private final GregorianCalendar birthDate;

    public Contact(String name, String firstNumber, String secondNumber, String firstEmail, String secondEmail, String contactAddress, GregorianCalendar birthDate, int contactColor) {
        this.name = name;
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.firstEmail = firstEmail;
        this.secondEmail = secondEmail;
        this.contactAddress = contactAddress;
        this.birthDate = birthDate;
        this.contactColor = contactColor;
    }

    public Contact(String name, String firstNumber, int contactColor) {
        this.name = name;
        this.firstNumber = firstNumber;
        this.contactColor = contactColor;
        this.secondNumber = null;
        this.firstEmail = null;
        this.secondEmail = null;
        this.contactAddress = null;
        this.birthDate = null;
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

    public int getContactColor() {
        return contactColor;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }


}
