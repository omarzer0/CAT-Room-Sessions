package az.zero.cat_room_session;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

import az.zero.cat_room_session.adapter.ContactAdapter;
import az.zero.cat_room_session.data.Contact;
import az.zero.cat_room_session.db.ContactDatabase;

@SuppressLint("NotifyDataSetChanged")
public class MainActivity extends AppCompatActivity {

    private ContactAdapter contactAdapter;
    private ArrayList<Contact> contacts = new ArrayList<>();
    private RecyclerView contactsRecyclerView;
    private ContactDatabase database;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiViews();
        setUpDB();
        setUpRV();
        setUpClicks();

        contacts.addAll(database.contactDao().getAll());

    }

    private void intiViews() {
        contactsRecyclerView = findViewById(R.id.rv_contacts);
        floatingActionButton = findViewById(R.id.fab_add_contact);
    }

    private void setUpDB() {
        database = Room.databaseBuilder(getApplicationContext(), ContactDatabase.class, "contact_db")
                // This is very bad!!
                // TODO: remove queries from the main thread
                .allowMainThreadQueries()
                .build();
    }


    private void setUpRV() {
        contactAdapter = new ContactAdapter(contacts);
        contactsRecyclerView.setAdapter(contactAdapter);
    }

    private void setUpClicks() {
        floatingActionButton.setOnClickListener(view -> {
            database.contactDao().insertContact(new Contact("omar " +
                    new Random().nextInt(1000), "1111", R.drawable.img));

            contacts.clear();
            contacts.addAll(database.contactDao().getAll());
            contactAdapter.notifyDataSetChanged();
        });
    }
}