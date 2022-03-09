package az.zero.cat_room_session.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import az.zero.cat_room_session.data.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {

    abstract public ContactDao contactDao();

    private static volatile ContactDatabase INSTANCE;

    // Double check locking
    public static ContactDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (ContactDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ContactDatabase.class, "contact_db").build();
                }
            }
        }
        return INSTANCE;
    }

    // Thread 1 -> database ref -> null -> create
    // Thread 2 -> database ref -> null -> create
    // Thread 2 -> database ref -> null -> create

    // [Thread1 , Thread2, Thread3]
    // Thread1 -> have lock             -> lock is free
    // Thread2 -> waiting....           -> have lock        -> lock is free
    // Thread2 -> waiting....           -> waiting....      -> have lock

}
