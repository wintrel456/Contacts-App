package com.gmail.l2t45s7e9.library

import android.content.Context
import com.gmail.l2t45s7e9.java.entity.BirthDateNotificationModel
import com.gmail.l2t45s7e9.java.entity.Contact
import com.gmail.l2t45s7e9.java.interactor.ContactDetailsInteractor
import com.gmail.l2t45s7e9.java.interactor.ContactDetailsRepository
import com.gmail.l2t45s7e9.java.interactor.ContactDetilsModel
import com.gmail.l2t45s7e9.java.interactor.CurrentDate
import com.gmail.l2t45s7e9.java.interactor.NotificationInteractor
import com.gmail.l2t45s7e9.java.interactor.NotificationRepository
import com.gmail.l2t45s7e9.library.domain.ContactDetailsViewModel
import com.gmail.l2t45s7e9.library.fragmentsState.ContactDetailsMapper
import com.gmail.l2t45s7e9.library.interfaces.DispatchersProvider
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.spekframework.spek2.Spek
import org.spekframework.spek2.dsl.Root
import org.spekframework.spek2.style.specification.Suite
import org.spekframework.spek2.style.specification.describe
import java.util.Calendar
import java.util.GregorianCalendar

@ExperimentalCoroutinesApi
object IntegrativeTest : Spek({
    val contactDetailsViewModelFactory = setUpMocks()
    val notificationRepository: NotificationRepository by memoized()
    val dateModel: CurrentDate by memoized()
    describe("Notification tests without leap year") {
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
        val factory by memoized { this.contactDetailsViewModelFactory() }
        mockDefaults(contact)

        it("Notification added") {
            every { dateModel.currentDate } returns GregorianCalendar(1999, Calendar.SEPTEMBER, 9)
            runBlockingTest {
                factory.loadContactDetails(contact.id, contact.contactColor)
                factory.setNotification()
            }
            verify {
                notificationRepository.setManager(
                    contact,
                    GregorianCalendar(2000, Calendar.SEPTEMBER, 8)
                )
            }
        }

        it("Notification added and BD in this year is not") {
            every { dateModel.currentDate } returns GregorianCalendar(1999, Calendar.SEPTEMBER, 7)
            runBlockingTest {
                factory.loadContactDetails(contact.id, contact.contactColor)
                factory.setNotification()
            }
            verify {
                notificationRepository.setManager(
                    contact,
                    GregorianCalendar(1999, Calendar.SEPTEMBER, 8)
                )
            }
        }

        it("Notification deleted") {
            every { dateModel.currentDate } returns GregorianCalendar(1999, Calendar.SEPTEMBER, 7)
            runBlockingTest {
                factory.loadContactDetails(contact.id, contact.contactColor)
                factory.cancelNotification()
            }
            verify { notificationRepository.cancelManager(contact) }
        }
    }
    describe("Notification test with leap year") {
        val birthDate = GregorianCalendar(1996, Calendar.FEBRUARY, 29)
        val contact = Contact(
            "0",
            "Pavel",
            "",
            "",
            "",
            "",
            "",
            birthDate,
            0
        )

        val factory by memoized { this.contactDetailsViewModelFactory() }
        mockDefaults(contact)

        it("Notification added to 29 February") {
            every { dateModel.currentDate } returns GregorianCalendar(1999, Calendar.MARCH, 2)
            runBlockingTest {
                factory.loadContactDetails(contact.id, contact.contactColor)
                factory.setNotification()
            }
            verify {
                notificationRepository.setManager(
                    contact,
                    GregorianCalendar(2000, Calendar.FEBRUARY, 29)
                )
            }
        }

        it("Notification added to 29 February of leap year") {
            every { dateModel.currentDate } returns GregorianCalendar(2000, Calendar.MARCH, 1)
            runBlockingTest {
                factory.loadContactDetails(contact.id, contact.contactColor)
                factory.setNotification()
            }
            verify {
                notificationRepository.setManager(
                    contact,
                    GregorianCalendar(2004, Calendar.FEBRUARY, 29)
                )
            }
        }
    }
})

@ExperimentalCoroutinesApi
private fun Root.installDispatchers(testDispatcher: TestCoroutineDispatcher) {
    @Suppress("UNUSED_VARIABLE") val dispatchers by memoized {
        object : DispatchersProvider {
            override fun io(): CoroutineDispatcher {
                return testDispatcher
            }

            override fun ui(): CoroutineDispatcher {
                return testDispatcher
            }

            override fun default(): CoroutineDispatcher {
                return testDispatcher
            }
        }
    }
}

@ExperimentalCoroutinesApi
private fun Root.instantTaskExecute() {
    val testDispatcher = TestCoroutineDispatcher()
    installDispatchers(testDispatcher)

    beforeEachTest {
        Dispatchers.setMain(testDispatcher)
    }

    afterEachTest {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}

@ExperimentalCoroutinesApi
@Suppress("UNUSED_VARIABLE")
fun Root.setUpMocks(): Suite.() -> ContactDetailsViewModel {
    instantTaskExecute()

    val dateModel by memoized {
        mockk<CurrentDate>(relaxed = true)
    }

    val context by memoized {
        mockk<Context>(relaxed = true)
    }

    val notificationRepository by memoized {
        mockk<NotificationRepository>(relaxed = true)
    }

    val contactDetailsRepository by memoized {
        mockk<ContactDetailsRepository>(relaxed = true)
    }

    val contactDetailsMapper by memoized {
        mockk<ContactDetailsMapper>(relaxed = true)
    }

    return {
        val dispatchers by memoized<DispatchersProvider>()
        val notificationInteractor: NotificationInteractor = BirthDateNotificationModel(
            dateModel,
            notificationRepository
        )
        val contactDetailsInteractor: ContactDetailsInteractor = ContactDetilsModel(
            contactDetailsRepository
        )
        ContactDetailsViewModel(
            contactDetailsInteractor = contactDetailsInteractor,
            notificationInteractor = notificationInteractor,
            contactDetailsMapper = contactDetailsMapper,
            dispatchersProvider = dispatchers
        )
    }
}

private fun Suite.mockDefaults(contact: Contact) {
    val contactDetailsRepository: ContactDetailsRepository by memoized()
    beforeEachTest {
        coEvery {
            contactDetailsRepository.loadDetailsInformation(contact.id, contact.contactColor)
        } returns flow { emit(contact) }
    }
}
