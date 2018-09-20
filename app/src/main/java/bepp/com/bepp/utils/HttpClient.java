package bepp.com.bepp.utils;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by charlie on 03/10/17.
 */

public class HttpClient {


    private HttpClient() {

    }

    private static final HttpClient INSTANCE = new HttpClient();

    private static String token;


    private final static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {


                    Request original = chain.request();
                    Request.Builder requestBuilder;
                    Log.i("Bepp","Token.."+token);
                    if(token != null){
                        Log.i("Bepp","2Token.."+token);
                        requestBuilder = original.newBuilder()
                                .header("token", token)
                                .addHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .method(original.method(), original.body()); // <-- this is the important line
                        Log.i("Bepp","3Token.."+token);
                    }else{
                        requestBuilder = original.newBuilder()
                                .method(original.method(), original.body()); // <-- this is the important line
                    }


                   if(chain.request().body() != null ) Log.i("body mensaje", chain.request().body().toString());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);

                }
            })
            .build();
    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        HttpClient.token = token;
    }

    public static HttpClient getINSTANCE() {
        return INSTANCE;
    }

    public static OkHttpClient getClient() {
        return client;
    }
}
