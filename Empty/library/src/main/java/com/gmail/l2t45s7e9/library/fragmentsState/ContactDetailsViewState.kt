package com.gmail.l2t45s7e9.library.fragmentsState

import com.gmail.l2t45s7e9.java.entity.Contact
import kotlinx.coroutines.flow.StateFlow

class ContactDetailsViewState(
    val contact: Contact?,//StateFlow<Contact?>,
    val birthDate:String,
    val addressState:Boolean,
    val addButtonText:String,
    val state:Boolean,
    val dateState: Boolean,
    val address: String
)