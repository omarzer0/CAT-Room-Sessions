package az.zero.cat_room_session;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import az.zero.cat_room_session.adapter.ContactAdapter;
import az.zero.cat_room_session.data.Contact;

public class MainActivity extends AppCompatActivity {

    private ContactAdapter contactAdapter;
    private ArrayList<Contact> contacts = new ArrayList<>();
    private RecyclerView contactsRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpRV();
        setUpClicks();


    }


    private void setUpRV() {
        contactsRecyclerView = findViewById(R.id.rv_contacts);
        contactAdapter = new ContactAdapter(contacts);
        contactsRecyclerView.setAdapter(contactAdapter);
    }

    private void setUpClicks() {
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_add_contact);
        floatingActionButton.setOnClickListener(view -> {
            contacts.add(new Contact("Omar " + (contacts.size() + 1), "01001111111", R.drawable.img));
            contactAdapter.notifyDataSetChanged();
        });
    }
}