package com.gmail.l2t45s7e9.library.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import com.gmail.l2t45s7e9.java.interactor.ContactDetailsInteractor
import com.gmail.l2t45s7e9.java.interactor.NotificationInteractor
import com.gmail.l2t45s7e9.library.interfaces.SchedulersProvider
import com.gmail.l2t45s7e9.java.entity.Contact
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ContactDetailsViewModel @Inject constructor(
    private val contactDetailsInteractor: ContactDetailsInteractor,
    private val notificationInteractor: NotificationInteractor
) : ViewModel() {

    private val contactDetailsMutableLiveData = MutableLiveData<Contact>()
    val contactDetailsLiveData: LiveData<Contact> get() = contactDetailsMutableLiveData

    fun loadContactDetails(id: String?, color: Int) = viewModelScope.launch {
        contactDetailsInteractor.getContactDetailsRepo(id, color)
            .map(contactDetailsMutableLiveData::setValue)
            .collect()
    }

    fun setNotification() {
        notificationInteractor.setNotification(contactDetailsMutableLiveData.value)
    }

    fun cancelNotification() {
        notificationInteractor.cancelNotification(contactDetailsMutableLiveData.value)
    }

    val status: Boolean
        get() = notificationInteractor.status(contactDetailsMutableLiveData.value)

}