package com.gmail.l2t45s7e9.library.dataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contactdata")
    List<ContactData> getAll();

    @Query("SELECT * FROM contactdata WHERE id LIKE :arg0")
    Single<List<ContactData>> loadContactForMap(String arg0);

    @Query("SELECT contactAddress FROM contactdata WHERE id LIKE :arg0")
    String loadContactById(String arg0);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ContactData contact);

}
