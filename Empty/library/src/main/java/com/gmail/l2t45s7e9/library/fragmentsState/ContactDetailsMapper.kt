package com.gmail.l2t45s7e9.library.fragmentsState

import android.content.Context
import com.gmail.l2t45s7e9.java.entity.Contact
import com.gmail.l2t45s7e9.library.R
import java.util.*

class ContactDetailsMapper(
    private val context: Context
) {

    private val formatDate: String by lazy(LazyThreadSafetyMode.NONE) {
        context.getString(R.string.date_format_for_contact_details)
    }

    fun create(contact:Contact?, state:Boolean): ContactDetailsViewState {
        val date = contact?.birthDate
        val addressState:Boolean
        val addButtonText:String
        val dateState:Boolean
        val birthDate:String
        val address:String
        when (date) {
            null ->{
                birthDate = context.getString(R.string.empty_date)
                dateState = false
            }
            else -> {
                birthDate = String.format(
                    Locale.getDefault(),
                    formatDate,
                    date.get(Calendar.DATE),
                    date.getDisplayName(
                        Calendar.MONTH, Calendar.LONG,
                        Locale.getDefault()
                    )
                ).toUpperCase(Locale.ROOT)
                dateState = true
            }
        }
        if (!contact?.contactAddress.isNullOrEmpty()){
            addressState = true
            addButtonText = context.getString(R.string.see_on_map_label)
            address  = contact?.contactAddress.toString()
        }else{
            addressState = false
            addButtonText = context.getString(R.string.button_add)
            address = context.getString(R.string.empty_address)
        }

        return ContactDetailsViewState(
            contact,
            birthDate,
            addressState,
            addButtonText,
            state,
            dateState,
            address
        )
    }
}