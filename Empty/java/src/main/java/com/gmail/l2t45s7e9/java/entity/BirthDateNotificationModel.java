package com.gmail.l2t45s7e9.java.entity;

import com.gmail.l2t45s7e9.java.interactor.CurrentDate;
import com.gmail.l2t45s7e9.java.interactor.NotificationInteractor;
import com.gmail.l2t45s7e9.java.interactor.NotificationRepository;
import java.util.Calendar;
import java.util.GregorianCalendar;
public class BirthDateNotificationModel implements NotificationInteractor {

    private final CurrentDate currentDate;
    private final NotificationRepository notificationRepository;

    public BirthDateNotificationModel(CurrentDate currentDate, NotificationRepository notificationRepository) {
        this.currentDate = currentDate;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void setNotification(Contact contact) {
        GregorianCalendar calendar = currentDate.getCurrentDate();
        GregorianCalendar date = contact.getBirthDate();
        if (date.get(Calendar.DATE) == 29 && date.get(Calendar.MONTH) == Calendar.FEBRUARY) {
            if (calendar.isLeapYear(calendar.get(Calendar.YEAR))) {
                calendar.add(Calendar.YEAR, 4);
            } else {
                while (!calendar.isLeapYear(calendar.get(Calendar.YEAR))) {
                    calendar.add(Calendar.YEAR, 1);
                }
            }
        } else if (calendar.get(Calendar.MONTH) >= date.get(Calendar.MONTH) && calendar.get(Calendar.DATE) > date.get(Calendar.DATE)) {
            calendar.add(Calendar.YEAR, 1);
        }
        calendar.set(Calendar.MONTH, date.get(Calendar.MONTH));
        calendar.set(Calendar.DATE, date.get(Calendar.DATE));
        notificationRepository.setManager(contact, calendar);
    }

    @Override
    public void cancelNotification(Contact contact) {
        notificationRepository.cancelManager(contact);
    }

    @Override
    public boolean status(Contact contact) {
        boolean contactNotificationStatus = false;
        if(contact!=null){
            contactNotificationStatus = notificationRepository.status(contact.getId());
        }
        return contactNotificationStatus;
    }

}
