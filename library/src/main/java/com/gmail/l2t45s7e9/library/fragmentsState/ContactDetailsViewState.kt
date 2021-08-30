package com.gmail.l2t45s7e9.library.fragmentsState

import com.gmail.l2t45s7e9.java.entity.Contact

data class ContactDetailsViewState(
    val contact: Contact?,
    val birthDate: String,
    val addressState: Boolean,
    val addButtonText: String,
    val state: Boolean,
    val dateState: Boolean,
    val address: String
)
