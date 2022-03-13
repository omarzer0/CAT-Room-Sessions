package az.zero.cat_room_session.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static final String BASE_URL = "https://2d9fbbd1-c990-43e3-9a32-0c588b00b8fc.mock.pstmn.io";
    private static volatile ContactApi CONTACT_API;

    private RetrofitInstance() {
    }

    public static ContactApi getContactApi() {
        if (CONTACT_API == null) {
            synchronized (RetrofitInstance.class) {
                if (CONTACT_API == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(getOkHttpClient())
                            .baseUrl(BASE_URL)
                            .build();

                    CONTACT_API = retrofit.create(ContactApi.class);
                }
            }
        }
        return CONTACT_API;
    }

    // interceptor
    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    // client
    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient().newBuilder()
                .addInterceptor(getHttpLoggingInterceptor())
                .build();
    }
}
