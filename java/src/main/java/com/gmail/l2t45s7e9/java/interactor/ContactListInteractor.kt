package com.gmail.l2t45s7e9.java.interactor

import com.gmail.l2t45s7e9.java.entity.Contact
import kotlinx.coroutines.flow.Flow

interface ContactListInteractor {
    suspend fun getContactListRepo(filterPattern: String): Flow<List<Contact>>
}
