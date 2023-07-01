/*
 * Copyright © 2017 Nedbank. All rights reserved.
 */

package in.base.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;


public class NetworkMonitorInterceptor implements Interceptor {

    private Context context;

    public NetworkMonitorInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(@NonNull final Chain chain) throws IOException {
        if (isConnected()) {
            return chain.proceed(chain.request());
        } else {
            throw new NoNetworkException();
        }
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }
}