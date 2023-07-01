package in.base.network;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import in.discountmart.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient_Brand {
    private static final String BASE_URL ="";
    private static final String CATALOGUE_URL = "";
    private static NetworkClient_Brand instance;
    private Context context;
    private Retrofit retrofit;
    private Retrofit retrofitForCatalogue;



    public static NetworkClient_Brand getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkClient_Brand();
            instance.context = context;
        }
        return instance;
    }

    public <T> T create(Class<T> service) {
        if (this.retrofit == null) {
            this.retrofit = createRetrofit(false);
        }
        return this.retrofit.create(service);
    }

    public <T> T createCatalogue(Class<T> service) {
        if (this.retrofitForCatalogue == null) {
            this.retrofitForCatalogue = createRetrofit(true);
        }
        return this.retrofitForCatalogue.create(service);
    }


    public OkHttpClient createOkHttpClient() {
        final Builder builder = new Builder();
        builder.retryOnConnectionFailure(true);
        if (BuildConfig.ENABLE_HTTP_LOGS) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(Level.BODY);
            builder.addInterceptor(interceptor);
        }

        builder.addInterceptor(new NetworkMonitorInterceptor(context));

        builder.readTimeout(1, TimeUnit.MINUTES);
        builder.writeTimeout(1, TimeUnit.MINUTES);
        builder.connectTimeout(1, TimeUnit.MINUTES);

        return builder.build();
    }

    private Retrofit createRetrofit(boolean isCatalogueUrl) {
        final OkHttpClient client = createOkHttpClient();

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(isCatalogueUrl ? CATALOGUE_URL : BASE_URL)
                //.baseUrl(BASE_URL)
                //.addConverterFactory(MoshiConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);
        return builder.build();
    }

    public void reload() {
        this.retrofit = null;
    }
}
