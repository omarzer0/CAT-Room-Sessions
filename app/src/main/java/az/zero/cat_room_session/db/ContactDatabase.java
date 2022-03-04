package az.zero.cat_room_session.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import az.zero.cat_room_session.data.Contact;

@Database(entities = {Contact.class},version = 1)
public abstract class ContactDatabase extends RoomDatabase {

    abstract public ContactDao contactDao();

}
