package com.gmail.l2t45s7e9.library;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.executor.TaskExecutor;
import com.gmail.l2t45s7e9.java.entity.BirthDateNotificationModel;
import com.gmail.l2t45s7e9.java.entity.Contact;
import com.gmail.l2t45s7e9.java.interactor.ContactDetailsInteractor;
import com.gmail.l2t45s7e9.java.interactor.ContactDetailsRepository;
import com.gmail.l2t45s7e9.java.interactor.ContactDetilsModel;
import com.gmail.l2t45s7e9.java.interactor.CurrentDate;
import com.gmail.l2t45s7e9.java.interactor.NotificationInteractor;
import com.gmail.l2t45s7e9.java.interactor.NotificationRepository;
import com.gmail.l2t45s7e9.library.domain.ContactDetailsViewModel;
import com.gmail.l2t45s7e9.library.interfaces.SchedulersProvider;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
public class IntegrativeTest {
    @Mock
    CurrentDate dateModel;
    @Mock
    NotificationRepository notificationRepository;
    @Mock
    ContactDetailsRepository contactDetailsRepository;
    @Mock
    SchedulersProvider schedulersProvider;

    private void installDelegate() {
        ArchTaskExecutor.getInstance().setDelegate(new TaskExecutor() {
            @Override
            public void executeOnDiskIO(@NonNull Runnable runnable) {
                runnable.run();
            }

            @Override
            public void postToMainThread(@NonNull Runnable runnable) {
                runnable.run();
            }

            @Override
            public boolean isMainThread() {
                return true;
            }
        });
    }

    @Test
    public void successfully_adding_notification() {
        installDelegate();
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
        Single<Contact> single = Single.fromCallable(() -> contact);
        Mockito.when(contactDetailsRepository.loadDetailsInformation(contact.getId(), contact.getContactColor())).thenReturn(single);
        Mockito.when(dateModel.getCurrentDate()).thenReturn(currentDate);
        Mockito.when(schedulersProvider.io()).thenReturn(Schedulers.trampoline());
        Mockito.when(schedulersProvider.ui()).thenReturn(Schedulers.trampoline());
        ContactDetailsInteractor contactDetailsInteractor = new ContactDetilsModel(contactDetailsRepository);
        NotificationInteractor notificationInteractor = new BirthDateNotificationModel(dateModel, notificationRepository);
        ContactDetailsViewModel contactDetailsViewModel = new ContactDetailsViewModel(contactDetailsInteractor, notificationInteractor, schedulersProvider);
        contactDetailsViewModel.loadContactDetails(contact.getId(), contact.getContactColor());
        contactDetailsViewModel.setNotification();
        Mockito.verify(notificationRepository).setManager(contact, addNotificationDate);
    }

    @Test
    public void successfully_adding_notification_when_birthday_in_this_year_is_not() {
        installDelegate();
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
        Single<Contact> single = Single.fromCallable(() -> contact);
        Mockito.when(contactDetailsRepository.loadDetailsInformation(contact.getId(), contact.getContactColor())).thenReturn(single);
        Mockito.when(dateModel.getCurrentDate()).thenReturn(currentDate);
        Mockito.when(schedulersProvider.io()).thenReturn(Schedulers.trampoline());
        Mockito.when(schedulersProvider.ui()).thenReturn(Schedulers.trampoline());
        ContactDetailsInteractor contactDetailsInteractor = new ContactDetilsModel(contactDetailsRepository);
        NotificationInteractor notificationInteractor = new BirthDateNotificationModel(dateModel, notificationRepository);
        ContactDetailsViewModel contactDetailsViewModel = new ContactDetailsViewModel(contactDetailsInteractor, notificationInteractor, schedulersProvider);
        contactDetailsViewModel.loadContactDetails(contact.getId(), contact.getContactColor());
        contactDetailsViewModel.setNotification();
        Mockito.verify(notificationRepository).setManager(contact, addNotificationDate);
    }

    @Test
    public void successfully_deleting_notification() {
        installDelegate();
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
        Single<Contact> single = Single.fromCallable(() -> contact);
        Mockito.when(contactDetailsRepository.loadDetailsInformation(contact.getId(), contact.getContactColor())).thenReturn(single);
        Mockito.when(dateModel.getCurrentDate()).thenReturn(currentDate);
        Mockito.when(schedulersProvider.io()).thenReturn(Schedulers.trampoline());
        Mockito.when(schedulersProvider.ui()).thenReturn(Schedulers.trampoline());
        ContactDetailsInteractor contactDetailsInteractor = new ContactDetilsModel(contactDetailsRepository);
        NotificationInteractor notificationInteractor = new BirthDateNotificationModel(dateModel, notificationRepository);
        ContactDetailsViewModel contactDetailsViewModel = new ContactDetailsViewModel(contactDetailsInteractor, notificationInteractor, schedulersProvider);
        contactDetailsViewModel.loadContactDetails(contact.getId(), contact.getContactColor());
        contactDetailsViewModel.cancelNotification();
        Mockito.verify(notificationRepository).cancelManager(contact);
    }

    @Test
    public void successfully_adding_notification_for_contact_who_was_born_on_29February() {
        installDelegate();
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
        Single<Contact> single = Single.fromCallable(() -> contact);
        Mockito.when(contactDetailsRepository.loadDetailsInformation(contact.getId(), contact.getContactColor())).thenReturn(single);
        Mockito.when(dateModel.getCurrentDate()).thenReturn(currentDate);
        Mockito.when(schedulersProvider.io()).thenReturn(Schedulers.trampoline());
        Mockito.when(schedulersProvider.ui()).thenReturn(Schedulers.trampoline());
        ContactDetailsInteractor contactDetailsInteractor = new ContactDetilsModel(contactDetailsRepository);
        NotificationInteractor notificationInteractor = new BirthDateNotificationModel(dateModel, notificationRepository);
        ContactDetailsViewModel contactDetailsViewModel = new ContactDetailsViewModel(contactDetailsInteractor, notificationInteractor, schedulersProvider);
        contactDetailsViewModel.loadContactDetails(contact.getId(), contact.getContactColor());
        contactDetailsViewModel.setNotification();
        Mockito.verify(notificationRepository).setManager(contact, addNotificationDate);
    }

    @Test
    public void successfully_adding_notification_for_contact_who_was_born_on_29February_of_leap_year() {
        installDelegate();
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
        Single<Contact> single = Single.fromCallable(() -> contact);
        Mockito.when(contactDetailsRepository.loadDetailsInformation(contact.getId(), contact.getContactColor())).thenReturn(single);
        Mockito.when(dateModel.getCurrentDate()).thenReturn(currentDate);
        Mockito.when(schedulersProvider.io()).thenReturn(Schedulers.trampoline());
        Mockito.when(schedulersProvider.ui()).thenReturn(Schedulers.trampoline());
        ContactDetailsInteractor contactDetailsInteractor = new ContactDetilsModel(contactDetailsRepository);
        NotificationInteractor notificationInteractor = new BirthDateNotificationModel(dateModel, notificationRepository);
        ContactDetailsViewModel contactDetailsViewModel = new ContactDetailsViewModel(contactDetailsInteractor, notificationInteractor, schedulersProvider);
        contactDetailsViewModel.loadContactDetails(contact.getId(), contact.getContactColor());
        contactDetailsViewModel.setNotification();
        Mockito.verify(notificationRepository).setManager(contact, addNotificationDate);
    }
}