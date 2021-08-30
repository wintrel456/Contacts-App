package com.gmail.l2t45s7e9.java.interactor

import com.gmail.l2t45s7e9.java.entity.Contact
import kotlinx.coroutines.flow.Flow

interface ContactListRepository {
    suspend fun loadShortInformation(filterPattern: String): Flow<List<Contact>>
}
