package com.gmail.l2t45s7e9.java.interactor

import com.gmail.l2t45s7e9.java.entity.Contact
import kotlinx.coroutines.flow.Flow

class ContactListModel(private val contactListRepository: ContactListRepository) :
    ContactListInteractor {
    override suspend fun getContactListRepo(filterPattern: String): Flow<List<Contact>> {
        return contactListRepository.loadShortInformation(filterPattern)
    }
}
