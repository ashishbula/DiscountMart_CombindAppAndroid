package in.base.network;

import android.content.Context;
import java.util.concurrent.TimeUnit;

import in.discountmart.BuildConfig;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient_Utility_1 {
    private static final String BASE_URL = "https://utilitywebapi.bisplindia.in/";
    //private static final String CATALOGUE_URL = "http://app.tttextiles.com/";
    private static final String CATALOGUE_URL = "https://api.datayuge.com/v1/";
    //private static final String BASE_URL = "http://testwebapi.bisplindia.in/";
    private static NetworkClient_Utility_1 instance;
    private Retrofit retrofit;
    private Context context;
    private Retrofit retrofitForCatalogue;


    public static NetworkClient_Utility_1 getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkClient_Utility_1();
            instance.context = context;
        }

        return instance;
    }

    public <T> T create(final Class<T> service) {
        if (retrofit == null) {
            retrofit = createRetrofit1(false);
        }

        return retrofit.create(service);
    }
    public <T> T createCatalogue(final Class<T> service) {
        if (retrofitForCatalogue == null) {
            retrofitForCatalogue = createRetrofit1(true);
        }

        return retrofitForCatalogue.create(service);
    }


    public OkHttpClient createOkHttpClient1() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.retryOnConnectionFailure(true);
        if (BuildConfig.ENABLE_HTTP_LOGS) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        builder.addInterceptor(new NetworkMonitorInterceptor(context));

        builder.readTimeout(2, TimeUnit.MINUTES);
        builder.writeTimeout(2, TimeUnit.MINUTES);
        builder.connectTimeout(2, TimeUnit.MINUTES);


        return builder.build();
    }

    public Dispatcher cancelOkHttpClient() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.retryOnConnectionFailure(false);
        builder.addInterceptor(new NetworkMonitorInterceptor(context));
        builder.build().dispatcher();


        return builder.build().dispatcher();
    }


    private Retrofit createRetrofit1(boolean isCatalogueUrl) {
        final OkHttpClient clientok = createOkHttpClient1();

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(isCatalogueUrl ? CATALOGUE_URL : BASE_URL)
                //.baseUrl(BASE_URL)
                //.addConverterFactory(MoshiConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())

                .client(clientok);
        return builder.build();
    }

    public void reload() {
        retrofit = null;
    }

    public void cancelRetrofit(){
        final OkHttpClient okHttpClient = createOkHttpClient1();

        okHttpClient.dispatcher();
    }





}
