package com.gmail.l2t45s7e9.java;

import com.gmail.l2t45s7e9.java.entity.BirthDateNotificationModel;
import com.gmail.l2t45s7e9.java.entity.Contact;
import com.gmail.l2t45s7e9.java.interactor.CurrentDate;
import com.gmail.l2t45s7e9.java.interactor.NotificationInteractor;
import com.gmail.l2t45s7e9.java.interactor.NotificationRepository;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ModuleTest {

    @Mock
    CurrentDate dateModel;
    @Mock
    NotificationRepository notificationRepository;

    @Test
    public void successfully_adding_notification() {
        GregorianCalendar addNotificationDate = new GregorianCalendar(2000, 8, 8);
        GregorianCalendar currentDate = new GregorianCalendar(1999, 8, 9);
        GregorianCalendar birthDate = new GregorianCalendar();
        birthDate.set(Calendar.DATE, 8);
        birthDate.set(Calendar.MONTH, Calendar.SEPTEMBER);
        Contact contact = new Contact(
                "0",
                "Ivan",
                "",
                "",
                "",
                "",
                "",
                birthDate,
                0
        );
        Mockito.when(dateModel.getCurrentDate()).thenReturn(currentDate);
        NotificationInteractor notificationInteractor = new BirthDateNotificationModel(dateModel, notificationRepository);
        notificationInteractor.setNotification(contact);
        Mockito.verify(notificationRepository).setManager(contact, addNotificationDate);
    }

    @Test
    public void successfully_adding_notification_when_birthday_in_this_year_is_not() {
        GregorianCalendar addNotificationDate = new GregorianCalendar(1999, Calendar.SEPTEMBER, 8);
        GregorianCalendar currentDate = new GregorianCalendar(1999, Calendar.SEPTEMBER, 7);
        GregorianCalendar birthDate = new GregorianCalendar();
        birthDate.set(Calendar.DATE, 8);
        birthDate.set(Calendar.MONTH, Calendar.SEPTEMBER);
        Contact contact = new Contact(
                "0",
                "Ivan",
                "",
                "",
                "",
                "",
                "",
                birthDate,
                0
        );
        Mockito.when(dateModel.getCurrentDate()).thenReturn(currentDate);
        NotificationInteractor notificationInteractor = new BirthDateNotificationModel(dateModel, notificationRepository);
        notificationInteractor.setNotification(contact);
        Mockito.verify(notificationRepository).setManager(contact, addNotificationDate);
    }

    @Test
    public void successfully_deleting_notification() {
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.YEAR, 1999);
        GregorianCalendar birthDate = new GregorianCalendar();
        birthDate.set(Calendar.DATE, 8);
        birthDate.set(Calendar.MONTH, Calendar.SEPTEMBER);
        Contact contact = new Contact(
                "0",
                "Ivan",
                "",
                "",
                "",
                "",
                "",
                birthDate,
                0
        );
        Mockito.when(dateModel.getCurrentDate()).thenReturn(currentDate);
        NotificationInteractor notificationInteractor = new BirthDateNotificationModel(dateModel, notificationRepository);
        notificationInteractor.cancelNotification(contact);
        Mockito.verify(notificationRepository).cancelManager(contact);
    }

    @Test
    public void successfully_adding_notification_for_contact_who_was_born_on_29February() {
        GregorianCalendar addNotificationDate = new GregorianCalendar(2000, Calendar.FEBRUARY, 29);
        GregorianCalendar currentDate = new GregorianCalendar(1999, Calendar.MARCH, 2);
        GregorianCalendar birthDate = new GregorianCalendar();
        birthDate.set(Calendar.DATE, 29);
        birthDate.set(Calendar.MONTH, Calendar.FEBRUARY);
        Contact contact = new Contact(
                "0",
                "Pavel",
                "",
                "",
                "",
                "",
                "",
                new GregorianCalendar(1996, 1, 29),
                0
        );
        Mockito.when(dateModel.getCurrentDate()).thenReturn(currentDate);
        NotificationInteractor notificationInteractor = new BirthDateNotificationModel(dateModel, notificationRepository);
        notificationInteractor.setNotification(contact);
        Mockito.verify(notificationRepository).setManager(contact, addNotificationDate);
    }

    @Test
    public void successfully_adding_notification_for_contact_who_was_born_on_29February_of_leap_year() {
        GregorianCalendar addNotificationDate = new GregorianCalendar(2004, Calendar.FEBRUARY, 29);
        GregorianCalendar currentDate = new GregorianCalendar(2000, Calendar.MARCH, 1);
        GregorianCalendar birthDate = new GregorianCalendar();
        birthDate.set(Calendar.DATE, 29);
        birthDate.set(Calendar.MONTH, Calendar.FEBRUARY);
        Contact contact = new Contact(
                "0",
                "Pavel",
                "",
                "",
                "",
                "",
                "",
                new GregorianCalendar(1996, 1, 29),
                0
        );
        Mockito.when(dateModel.getCurrentDate()).thenReturn(currentDate);
        NotificationInteractor notificationInteractor = new BirthDateNotificationModel(dateModel, notificationRepository);
        notificationInteractor.setNotification(contact);
        Mockito.verify(notificationRepository).setManager(contact, addNotificationDate);
    }
}