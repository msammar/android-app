package se.lublin.mumla.network;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit : Rest client
 */
public class RestClient {

    private static RestClient instance;

    private ApiInterface mApiInterface;


    /**
     * Get singleton instance for RestClient class
     *
     * @return Singleton instance
     */
    public static RestClient getInstance() {
        if (instance == null) {
            instance = new RestClient();
        }
        return instance;
    }


    /**
     * Clear singleton instance
     */
    public static void clearInstance() {
        instance = null;
    }

    /**
     * Create OkHttp client
     *
     * @return OkHttpClient
     */
    private OkHttpClient createOkHttpClient() {
        //interceptor to log request and response info
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(ApiInterface.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(ApiInterface.READ_TIMEOUT, TimeUnit.SECONDS);

        builder.addInterceptor(httpLoggingInterceptor);
        return builder.build();
    }


    /**
     * Get api service for cloud server
     *
     * @return ApiService
     */
    public ApiInterface getApiService() {
        if (mApiInterface == null)
            mApiInterface = createApiServiceForLoginAuth();
        return mApiInterface;
    }


    private ApiInterface createApiServiceForLoginAuth() {
        final OkHttpClient okHttpClient = createOkHttpClient();

        //prepare retrofit client
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        //create request; this instance will be used to make api call on server
        return retrofit.create(ApiInterface.class);
    }

}