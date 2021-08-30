package com.gmail.l2t45s7e9.library.repository

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.gmail.l2t45s7e9.java.entity.Contact
import com.gmail.l2t45s7e9.java.interactor.ContactDetailsRepository
import com.gmail.l2t45s7e9.library.dataBase.ContactAddressDataBase
import com.gmail.l2t45s7e9.library.interfaces.DispatchersProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext


class ContactDetailsRepositoryImpl(
    private val context: Context,
    private val db: ContactAddressDataBase,
    private val dispatchersProvider: DispatchersProvider
) : ContactDetailsRepository {
    private val contentResolver: ContentResolver = context.contentResolver

    override suspend fun loadDetailsInformation(id: String?, color: Int) =
        withContext(dispatchersProvider.io()) {
            val address = getAddress(id).single()
            val contactsRepositoryDelegate = ContactsRepositoryDelegate(context)
            var contact: Contact? = null
            val contactCursor: Cursor? = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                ContactsContract.Contacts._ID + "=" + id,
                null,
                ContactsContract.Contacts.DISPLAY_NAME
            )
            contactCursor.use { cursor ->
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
                    }
                }
                cursor?.close()
            }

            return@withContext flow {
                emit(contact)
            }
        }

    private fun getAddress(id: String?): Flow<String> {
        return flow { emit(db.contactDao().loadContactById(id)) }.flowOn(Dispatchers.IO)
    }

}