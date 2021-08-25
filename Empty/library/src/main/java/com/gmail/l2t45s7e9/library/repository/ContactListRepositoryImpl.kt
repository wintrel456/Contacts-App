package com.gmail.l2t45s7e9.library.repository

import com.gmail.l2t45s7e9.java.interactor.ContactListRepository
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import com.gmail.l2t45s7e9.java.entity.Contact
import com.gmail.l2t45s7e9.library.repository.ContactsRepositoryDelegate
import com.gmail.l2t45s7e9.library.R
import android.provider.ContactsContract
import com.gmail.l2t45s7e9.library.interfaces.DispatchersProvider
import com.gmail.l2t45s7e9.library.interfaces.SchedulersProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.*

class ContactListRepositoryImpl(
    private val context: Context,
    private val dispatchersProvider: DispatchersProvider
    ) : ContactListRepository {
    private val contentResolver: ContentResolver = context.contentResolver
    override suspend fun loadShortInformation(filterPattern: String) = withContext(dispatchersProvider.io()) {
        val contactsRepositoryDelegate = ContactsRepositoryDelegate(context)
        val contacts: MutableList<Contact> = ArrayList()
        val set: MutableSet<String> = HashSet()
        val random = Random()
        val colors = context.resources.getIntArray(R.array.colors_list)
        val contactCursor: Cursor? = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?", arrayOf("%$filterPattern%"),
            ContactsContract.Contacts.DISPLAY_NAME
        )
        contactCursor.use {
            while (it?.moveToNext() == true) {
                val id = it.getString(it.getColumnIndex(ContactsContract.Contacts._ID))
                val name = contactsRepositoryDelegate.getName(it, id)
                val firstNumber = contactsRepositoryDelegate.getNumbers(it, id)[0]
                val contact = Contact(
                    id,
                    name,
                    firstNumber,
                    colors[random.nextInt(colors.size)]
                )
                if (!set.contains(firstNumber)) {
                    contacts.add(contact)
                    set.add(firstNumber)
                }
            }
            it?.close()
        }

        return@withContext flow {
            emit(contacts)
        }
    }

}