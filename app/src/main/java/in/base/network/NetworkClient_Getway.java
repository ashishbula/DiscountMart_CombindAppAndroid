package in.base.network;

import android.content.Context;



import java.util.concurrent.TimeUnit;

import in.discountmart.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class NetworkClient_Getway {

   private static final String BASE_URL = "http://paymentapi.thediscountapp.in/";
    private static  String CATALOGUE_URL = "";//http://dtil.biztadka.com/

    private static NetworkClient_Getway instance;
    private Retrofit retrofit;
    private Context context;
    private Retrofit retrofitForCatalogue;

    public static NetworkClient_Getway getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkClient_Getway();
            instance.context = context;
        }

        return instance;
    }

    public <T> T create(final Class<T> service) {
        if (retrofit == null) {
            retrofit = createRetrofit(false);
        }

        return retrofit.create(service);
    }

    public <T> T createCatalogue(final Class<T> service) {
        if (retrofitForCatalogue == null) {
            retrofitForCatalogue = createRetrofit(true);
        }

        return retrofitForCatalogue.create(service);
    }

    public OkHttpClient createOkHttpClient() {
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

    private Retrofit createRetrofit(boolean isCatalogueUrl) {
        final OkHttpClient client = createOkHttpClient();

        //LoginResponse loginResponse =new LoginResponse();
        //loginResponse=new LoginPreferences_Utility(context).getLoggedinUser();
        //CATALOGUE_URL=loginResponse.getDomainName() +"/";
        final Retrofit.Builder builder = new Retrofit.Builder()
                //.baseUrl(isCatalogueUrl ? CATALOGUE_URL : BASE_URL)
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client);
        return builder.build();
    }

    public void reload() {
        retrofit = null;
    }


}
