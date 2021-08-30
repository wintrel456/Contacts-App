package com.gmail.l2t45s7e9.empty.di.database

import android.content.Context
import androidx.room.Room
import com.gmail.l2t45s7e9.library.dataBase.ContactAddressDataBase
import com.gmail.l2t45s7e9.library.dataBase.ContactDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseProviderModule {
    @Provides
    @Singleton
    fun provideDataBase(context: Context): ContactAddressDataBase {
        return Room.databaseBuilder(
            context,
            ContactAddressDataBase::class.java,
            "contact-map"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(contactAddressDataBase: ContactAddressDataBase): ContactDao {
        return contactAddressDataBase.contactDao()
    }
}
