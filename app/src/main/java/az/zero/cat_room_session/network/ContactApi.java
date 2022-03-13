package az.zero.cat_room_session.network;

import java.util.List;

import az.zero.cat_room_session.data.Contact;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ContactApi {


    @GET("contacts")
    Call<List<Contact>> getNetworkContacts();

    // https://2d9fbbd1-c990-43e3-9a32-0c588b00b8fc.mock.pstmn.io
    // contacts
}
