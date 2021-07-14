package com.gmail.l2t45s7e9.library.dataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.gmail.l2t45s7e9.java.entity.Contact;
import com.gmail.l2t45s7e9.java.interactor.ContactDao;
@Database(entities = {Contact.class}, version = 2)
public abstract class ContactAddressDataBase extends RoomDatabase {
    public abstract ContactDao contactDao();
}
