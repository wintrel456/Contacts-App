package com.gmail.l2t45s7e9.library.dataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ContactData.class}, version = 1)
public abstract class ContactAddressDataBase extends RoomDatabase {
    public abstract ContactDao contactDao();
}
