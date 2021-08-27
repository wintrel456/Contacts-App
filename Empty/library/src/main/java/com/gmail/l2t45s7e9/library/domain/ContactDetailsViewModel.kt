package com.gmail.l2t45s7e9.library.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import com.gmail.l2t45s7e9.java.interactor.ContactDetailsInteractor
import com.gmail.l2t45s7e9.java.interactor.NotificationInteractor
import com.gmail.l2t45s7e9.java.entity.Contact
import com.gmail.l2t45s7e9.library.fragmentsState.ContactDetailsMapper
import com.gmail.l2t45s7e9.library.interfaces.DispatchersProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ContactDetailsViewModel @Inject constructor(
    private val contactDetailsInteractor: ContactDetailsInteractor,
    private val notificationInteractor: NotificationInteractor,
    private val contactDetailsMapper: ContactDetailsMapper,
    dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val contactDetailsMutableLiveData = MutableStateFlow<Contact?>(null)
    val contactDetailsLiveData = contactDetailsMutableLiveData.asStateFlow().map {
        contactDetailsMapper.create(it, status)
    }.flowOn(dispatchersProvider.default())

    fun loadContactDetails(id: String?, color: Int) = viewModelScope.launch {
        contactDetailsInteractor.getContactDetailsRepo(id, color)
            .map(contactDetailsMutableLiveData::emit)
            .collect()
    }

    fun setNotification() {
        notificationInteractor.setNotification(contactDetailsMutableLiveData.value)
    }

    fun cancelNotification() {
        notificationInteractor.cancelNotification(contactDetailsMutableLiveData.value)
    }

    private val status: Boolean
        get() = notificationInteractor.status(contactDetailsMutableLiveData.value)

}