package com.gmail.l2t45s7e9.java.interactor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.gmail.l2t45s7e9.java.entity.Contact;
import io.reactivex.rxjava3.core.Single;
import java.util.List;
@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact")
    List<Contact> getAll();

    @Query("SELECT * FROM contact WHERE id LIKE :arg0")
    Single<List<Contact>> loadContactForMap(String arg0);

    @Query("SELECT contactAddress FROM contact WHERE id LIKE :arg0")
    Single<String> loadContactById(String arg0);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Contact contact);

    @Update
    void update(Contact contact);
}
