package se.lublin.mumla.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import se.lublin.mumla.model.login.LoginAccessToken;
import se.lublin.mumla.model.login.LoginResponse;
import se.lublin.mumla.model.location.UpdateLocationResponse;

/**
 * All the APIs (Server calls through the App) are written here.
 */
public interface ApiInterface {

    String BASE_URL = "https://voip.swiftptt.com/api/web/";

    long CONNECTION_TIMEOUT = 60;
    long READ_TIMEOUT = 60;

    @FormUrlEncoded
    @POST("mlogin")
    Call<LoginAccessToken> getAccessToken(@Field("username") String username, @Field("password") String password, @Field("network_name") String networkName);

    @GET("login")
    Call<LoginResponse> login(@Header("access-token") String access_token);

    @FormUrlEncoded
    @PUT("devices/update")
    Call<UpdateLocationResponse> updateLocation(@Header("access-token") String access_token, @Field("battery") String battery, @Field("lat") String latitude, @Field("long") String longitude);

}