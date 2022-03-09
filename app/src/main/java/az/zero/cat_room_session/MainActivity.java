package az.zero.cat_room_session;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import az.zero.cat_room_session.adapter.ContactAdapter;
import az.zero.cat_room_session.data.Contact;
import az.zero.cat_room_session.db.ContactDatabase;

@SuppressLint("NotifyDataSetChanged")
public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private ContactAdapter contactAdapter;
    private ArrayList<Contact> contacts = new ArrayList<>();
    private RecyclerView contactsRecyclerView;
    private ContactDatabase database;
    private FloatingActionButton floatingActionButton;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiViews();
        setUpDB();
        setUpRV();
        setUpClicks();
        observeData();

    }

    private void observeData() {
        database.contactDao().getAll().observe(this, dbContacts -> {
            contacts.clear();
            contacts.addAll(dbContacts);
            contactAdapter.notifyDataSetChanged();
        });
    }

    private void intiViews() {
        contactsRecyclerView = findViewById(R.id.rv_contacts);
        floatingActionButton = findViewById(R.id.fab_add_contact);
    }

    private void setUpDB() {
        database = ContactDatabase.getDatabase(this);
        Log.d(TAG, "setUpDB: " + database.hashCode());
    }


    private void setUpRV() {
        contactAdapter = new ContactAdapter(contacts);
        contactsRecyclerView.setAdapter(contactAdapter);
    }

    private void setUpClicks() {
        floatingActionButton.setOnClickListener(view -> {
            executorService.execute(() -> {
                Contact contact = new Contact("omar " + new Random().nextInt(1000), "1111", R.drawable.img);
                database.contactDao().insertContact(contact);
            });
        });
    }
}