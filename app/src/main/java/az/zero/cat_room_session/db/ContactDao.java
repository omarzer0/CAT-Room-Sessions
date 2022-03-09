package az.zero.cat_room_session.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import az.zero.cat_room_session.data.Contact;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contact ORDER BY id DESC")
    LiveData<List<Contact>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContact(Contact contact);

}
