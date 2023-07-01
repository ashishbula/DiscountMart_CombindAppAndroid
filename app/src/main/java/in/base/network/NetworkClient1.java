package in.base.network;

import android.content.Context;



import java.util.concurrent.TimeUnit;

import in.discountmart.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient1 {

    private static final String BASE_URL = "https://cpanel.discountmart.in/";
    //private static final String CATALOGUE_URL = "http://app.tttextiles.com/";
    private static NetworkClient1 instance;
    private Retrofit retrofit;
    private Context context;
    private Retrofit retrofitForCatalogue;


    public static NetworkClient1 getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkClient1();
            instance.context = context;
        }

        return instance;
    }

    public <T> T create(final Class<T> service) {
        if (retrofit == null) {
            retrofit = createRetrofit1();
        }

        return retrofit.create(service);
    }

   /* public <T> T createCatalogue(final Class<T> service) {
        if (retrofitForCatalogue == null) {
            retrofitForCatalogue = createRetrofit(true);
        }

        return retrofitForCatalogue.create(service);
    }*/

    public OkHttpClient createOkHttpClient1() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.retryOnConnectionFailure(true);
        if (BuildConfig.ENABLE_HTTP_LOGS) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        builder.addInterceptor(new NetworkMonitorInterceptor(context));

        builder.readTimeout(1, TimeUnit.MINUTES);
        builder.writeTimeout(1, TimeUnit.MINUTES);
        builder.connectTimeout(1, TimeUnit.MINUTES);

        return builder.build();
    }

    private Retrofit createRetrofit1() {
        final OkHttpClient clientok = createOkHttpClient1();

        final Retrofit.Builder builder = new Retrofit.Builder()
                //.baseUrl(isCatalogueUrl ? CATALOGUE_URL : BASE_URL)
                .baseUrl(BASE_URL)
                //.addConverterFactory(MoshiConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())

                .client(clientok);
        return builder.build();
    }

    public void reload() {
        retrofit = null;
    }


}
