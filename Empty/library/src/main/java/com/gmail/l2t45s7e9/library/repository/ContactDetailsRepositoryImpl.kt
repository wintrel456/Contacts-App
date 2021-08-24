package com.gmail.l2t45s7e9.library.repository

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.gmail.l2t45s7e9.java.entity.Contact
import com.gmail.l2t45s7e9.java.interactor.ContactDetailsRepository
import com.gmail.l2t45s7e9.library.dataBase.ContactAddressDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class ContactDetailsRepositoryImpl(
    private val context: Context,
    contactAddressDataBase: ContactAddressDataBase
) : ContactDetailsRepository {
    private val contentResolver: ContentResolver = context.contentResolver
    private val db: ContactAddressDataBase = contactAddressDataBase

    override suspend fun loadDetailsInformation(id: String?, color: Int) = withContext(Dispatchers.IO) {
        val address = getAddress(id).single()
        val contactsRepositoryDelegate = ContactsRepositoryDelegate(context)
        var contact: Contact? = null
        var cursor: Cursor? = null
        try {
            cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                ContactsContract.Contacts._ID + "=" + id,
                null,
                ContactsContract.Contacts.DISPLAY_NAME
            )
            while (cursor?.moveToNext() == true) {
                val name = contactsRepositoryDelegate.getName(cursor, id)
                val firstNumber = contactsRepositoryDelegate.getNumbers(cursor, id)[0]
                val secondNumber = contactsRepositoryDelegate.getNumbers(cursor, id)[1]
                val firstEmail = contactsRepositoryDelegate.getEmails(cursor, id)[0]
                val secondEmail = contactsRepositoryDelegate.getEmails(cursor, id)[1]
                val birthDate = contactsRepositoryDelegate.getBirthDate(cursor, id)
                contact = id?.let {
                    Contact(
                        it,
                        name,
                        firstNumber,
                        secondNumber,
                        firstEmail,
                        secondEmail,
                        address,
                        birthDate,
                        color
                    )
                }!!
            }
        } finally {
            cursor?.close()
        }
        return@withContext flow {
            emit(contact!!)
        }
    }

    private fun getAddress(id: String?): Flow<String> {
        return flow{emit(db.contactDao().loadContactById(id))}.flowOn(Dispatchers.IO)
    }


}