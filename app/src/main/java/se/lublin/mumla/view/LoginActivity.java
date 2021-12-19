package se.lublin.mumla.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.jetbrains.annotations.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.lublin.mumla.R;
import se.lublin.mumla.Settings;
import se.lublin.mumla.app.MumlaActivity;
import se.lublin.mumla.model.login.LoginAccessToken;
import se.lublin.mumla.model.login.LoginResponse;
import se.lublin.mumla.network.RestClient;

public class LoginActivity extends AppCompatActivity {

    private Settings mSettings;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private EditText networkNameEditText;
    private Button loginButton;
    private TextView errorText;

    private String userName = "user";
    private String password = "123";
    private String networkName = "swiftptt";

    private SharedPreferences sharedPreferences;

    Call<LoginAccessToken> loginAccessTokenCall = null;
    Call<LoginResponse> loginResponseCall = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mSettings = Settings.getInstance(this);
        setTheme(Settings.getInstance(this).getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sharedPreferences.getBoolean(Settings.PREF_IS_LOGGED_IN, false)) {
            launchPlumbleActivity();
            return;
        }

        userNameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        networkNameEditText = findViewById(R.id.networkname);
        loginButton = findViewById(R.id.login);
        errorText = findViewById(R.id.errorText);

        initTextWatcher();
        initClickListener();
    }

    private void initTextWatcher() {
        userNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                userName = s.toString();
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                password = s.toString();
            }
        });

        networkNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                networkName = s.toString();
            }
        });
    }

    private void showErrorToast() {
        errorText.setVisibility(View.VISIBLE);
        errorText.setText(getString(R.string.error_text_before_on_login_click));
    }

    private void showLoginError() {
        errorText.setVisibility(View.VISIBLE);
        errorText.setText(getString(R.string.login_error));
    }


    private void doLogin() {

        loginAccessTokenCall = RestClient.getInstance().getApiService().getAccessToken(userName, password, networkName);

        loginAccessTokenCall.enqueue(new Callback<LoginAccessToken>() {
            @Override
            public void onResponse(Call<LoginAccessToken> call, Response<LoginAccessToken> response) {
                //handle response
                if (response.isSuccessful()) {
                    LoginAccessToken loginAccessToken = response.body();
                    if (loginAccessToken != null && loginAccessToken.getAccessToken() != null) {

                        loginUsingAceessToken(loginAccessToken.getAccessToken());

                    } else {
                        showLoginError();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginAccessToken> call, Throwable t) {

            }
        });

    }

    private void loginUsingAceessToken(String accessToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        loginResponseCall = RestClient.getInstance().getApiService().login(accessToken);

        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse != null) {
                    editor.putBoolean(Settings.PREF_IS_LOGGED_IN, true);
                    editor.putString(Settings.ACCESS_TOKEN, accessToken);
                    editor.putString(Settings.SERVER_PORT, loginResponse.getNetwork().getPort() + "");
                    editor.putString(Settings.SERVER_ADDRESS, loginResponse.getNetwork().getVoipAddress());
                    editor.putString(Settings.USER_PASSWORD, password);
                    editor.putString(Settings.USERNAME, loginResponse.getName());
                    editor.putString(Settings.NETWORK_NAME, loginResponse.getNetwork().getNetworkName());
                    editor.apply();
                    launchPlumbleActivity();
                } else {
                    editor.putBoolean(Settings.PREF_IS_LOGGED_IN, false);
                    editor.apply();
                    showLoginError();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                editor.putBoolean(Settings.PREF_IS_LOGGED_IN, false);
                editor.apply();
                showLoginError();
            }
        });
    }

    private void launchPlumbleActivity() {
        Intent intent = new Intent(this, MumlaActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void initClickListener() {
        loginButton.setOnClickListener(v -> {
            if (userName.trim().isEmpty() || password.trim().isEmpty() || networkName.isEmpty()) {
                showErrorToast();
            } else {
                errorText.setVisibility(View.GONE);
                doLogin();
            }
        });
    }
}
