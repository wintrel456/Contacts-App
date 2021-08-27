package com.gmail.l2t45s7e9.java

import com.gmail.l2t45s7e9.java.entity.BirthDateNotificationModel
import com.gmail.l2t45s7e9.java.entity.Contact
import com.gmail.l2t45s7e9.java.interactor.CurrentDate
import com.gmail.l2t45s7e9.java.interactor.NotificationInteractor
import com.gmail.l2t45s7e9.java.interactor.NotificationRepository
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.util.*

object ModuleTests : Spek({
    val dateModel: CurrentDate = mock()
    val notificationRepository: NotificationRepository = mock()

    describe("Successfully adding notification") {
        val addNotificationDate = GregorianCalendar(2000, Calendar.SEPTEMBER, 8)
        val currentDate = GregorianCalendar(1999, Calendar.SEPTEMBER, 9)
        val birthDate = GregorianCalendar()

        birthDate[Calendar.DATE] = 8
        birthDate[Calendar.MONTH] = Calendar.SEPTEMBER

        val contact = Contact(
                "0",
                "Ivan",
                "",
                "",
                "",
                "",
                "",
                birthDate,
                0
        )
        Mockito.`when`(dateModel.currentDate).then {
            currentDate
        }
        val notificationInteractor: NotificationInteractor = BirthDateNotificationModel(
                dateModel,
                notificationRepository
        )
        notificationInteractor.setNotification(contact)
        Mockito.verify(notificationRepository).setManager(contact, addNotificationDate)
    }

    describe("Successfully adding notification when birthday in this year is not") {
        val addNotificationDate = GregorianCalendar(1999, Calendar.SEPTEMBER, 8)
        val currentDate = GregorianCalendar(1999, Calendar.SEPTEMBER, 7)
        val birthDate = GregorianCalendar()
        birthDate[Calendar.DATE] = 8
        birthDate[Calendar.MONTH] = Calendar.SEPTEMBER
        val contact = Contact(
                "0",
                "Ivan",
                "",
                "",
                "",
                "",
                "",
                birthDate,
                0
        )
        Mockito.`when`(dateModel.currentDate).then {
            currentDate
        }
        val notificationInteractor: NotificationInteractor = BirthDateNotificationModel(
                dateModel,
                notificationRepository
        )
        notificationInteractor.setNotification(contact)
        Mockito.verify(notificationRepository).setManager(contact, addNotificationDate)
    }

    describe("Successfully deleting notification") {
        val currentDate = GregorianCalendar(1999, Calendar.SEPTEMBER, 7)
        val birthDate = GregorianCalendar()
        birthDate.set(Calendar.DATE, 8)
        birthDate.set(Calendar.MONTH, Calendar.SEPTEMBER)
        val contact = Contact(
                "0",
                "Ivan",
                "",
                "",
                "",
                "",
                "",
                birthDate,
                0
        )
        Mockito.`when`(dateModel.currentDate).then {
            currentDate
        }
        val notificationInteractor: NotificationInteractor = BirthDateNotificationModel(
                dateModel,
                notificationRepository
        )
        notificationInteractor.cancelNotification(contact)
        Mockito.verify(notificationRepository).cancelManager(contact)
    }

    describe("Successfully adding notification for contact who was born on 29 February") {
        val addNotificationDate = GregorianCalendar(2000, Calendar.FEBRUARY, 29)
        val currentDate = GregorianCalendar(1999, Calendar.MARCH, 2)
        val birthDate = GregorianCalendar()
        birthDate[Calendar.DATE] = 29
        birthDate[Calendar.MONTH] = Calendar.FEBRUARY
        val contact = Contact(
                "0",
                "Pavel",
                "",
                "",
                "",
                "",
                "",
                GregorianCalendar(1996, 1, 29),
                0
        )
        Mockito.`when`(dateModel.currentDate).then {
            currentDate
        }
        val notificationInteractor: NotificationInteractor = BirthDateNotificationModel(
                dateModel,
                notificationRepository
        )
        notificationInteractor.setNotification(contact)
        Mockito.verify(notificationRepository).setManager(contact, addNotificationDate)
    }

    describe("Successfully adding notification for contact who was born on 29 February of leap year") {
        val addNotificationDate = GregorianCalendar(2004, Calendar.FEBRUARY, 29)
        val currentDate = GregorianCalendar(2000, Calendar.MARCH, 1)
        val birthDate = GregorianCalendar()
        birthDate[Calendar.DATE] = 29
        birthDate[Calendar.MONTH] = Calendar.FEBRUARY
        val contact = Contact(
                "0",
                "Pavel",
                "",
                "",
                "",
                "",
                "",
                GregorianCalendar(1996, 1, 29),
                0
        )
        Mockito.`when`(dateModel.currentDate).then {
            currentDate
        }
        val notificationInteractor: NotificationInteractor = BirthDateNotificationModel(
                dateModel,
                notificationRepository
        )
        notificationInteractor.setNotification(contact)
        Mockito.verify(notificationRepository).setManager(contact, addNotificationDate)
    }

})


