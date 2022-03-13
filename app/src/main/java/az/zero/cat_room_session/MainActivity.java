package az.zero.cat_room_session;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import az.zero.cat_room_session.adapter.ContactAdapter;
import az.zero.cat_room_session.data.Contact;
import az.zero.cat_room_session.db.ContactDatabase;
import az.zero.cat_room_session.network.ContactApi;
import az.zero.cat_room_session.network.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        setUpRetrofit();

    }

    private void setUpRetrofit() {
        ContactApi api = RetrofitInstance.getContactApi();
        Log.d(TAG, "setUpRetrofit: " + api.hashCode());

        api.getNetworkContacts().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                // server has your request
                // 401 404 400 500 300 fail
                // 200-299 success
                if (response.isSuccessful()) {
                    List<Contact> networkContacts = response.body();
                    if (networkContacts != null) {
                        // your data,,,,
//                        contacts.clear();
//                        contacts.addAll(networkContacts);
//                        contactAdapter.notifyDataSetChanged();
                        executorService.execute(() -> {
                            for (Contact contact : networkContacts) {
                                database.contactDao().insertContact(contact);
                            }
                        });

                    } else {
                        // null
                        Log.d(TAG, "onResponse: null data");
                    }
                } else {
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                // server didn't receive the request .... ex: No internet connection\
                Log.d(TAG, "onResponse: no internet connection");

            }
        });
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
                Contact contact = new Contact("omar " + new Random().nextInt(1000), "1111", "");
                database.contactDao().insertContact(contact);
            });
        });
    }
}