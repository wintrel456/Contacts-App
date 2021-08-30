package com.gmail.l2t45s7e9.library.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ContactData(
    @PrimaryKey
    val id: String,
    val contactAddress: String,
    val latitude: Double,
    val longitude: Double
)