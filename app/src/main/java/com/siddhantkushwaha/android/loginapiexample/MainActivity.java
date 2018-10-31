package com.siddhantkushwaha.android.loginapiexample;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;

    // YOUR urlSubstring should look like this if your callback url is "http://139.59.58.1/login/token"
    static final String urlSubstring = "http://139.59.58.1/login/";

    // TODO !! change these variables accordingly !!
    static final String clientId = "";
    static final String clientSecret = "";

    static final String url = "https://serene-wildwood-35121.herokuapp.com/login/" + clientId;

    private Retrofit retrofit;
    private static final String base_url = "https://serene-wildwood-35121.herokuapp.com";
    private static final String get_details_url = "oauth/getDetails";

    public interface LoginAPI {
        @POST(get_details_url)
        Call<JsonObject> getData(@Body JsonObject data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.login);
        textView = findViewById(R.id.res);

        retrofit = new Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create()).build();

        Intent intent = getIntent();
        String token = intent.getStringExtra("TOKEN");

        if (token != null) {
            LoginAPI loginAPI = retrofit.create(LoginAPI.class);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("token", token);
            jsonObject.addProperty("secret", clientSecret);

            Log.i(MainActivity.class.toString(), jsonObject.toString());

            Call<JsonObject> call = loginAPI.getData(jsonObject);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {

                    if (response.isSuccessful()) {
                        Log.i(MainActivity.class.toString(), "Logged In Successfully");
                        Log.i(MainActivity.class.toString(), response.body().toString());

                        // TODO: do stuff here once logged in

                        textView.setText(response.body().toString());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, Throwable t) {

                    Log.e(MainActivity.class.toString(), t.getMessage());
                }
            });
        }
    }

    public void login(View view) {

        Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
        startActivity(intent);
    }
}
