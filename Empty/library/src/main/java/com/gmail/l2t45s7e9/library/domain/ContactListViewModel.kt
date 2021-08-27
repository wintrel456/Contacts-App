package com.gmail.l2t45s7e9.library.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.l2t45s7e9.java.entity.Contact
import com.gmail.l2t45s7e9.java.interactor.ContactListInteractor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactListViewModel @Inject constructor(
    private val contactListInteractor: ContactListInteractor
) : ViewModel() {

    private val contactListMutableLiveData = MutableLiveData<List<Contact>>()
    val listLiveData: LiveData<List<Contact>> get() = contactListMutableLiveData
    fun loadContactList(filterPattern: String) = viewModelScope.launch {
        contactListInteractor.getContactListRepo(filterPattern)
            .map(contactListMutableLiveData::setValue)
            .collect()
    }
}
