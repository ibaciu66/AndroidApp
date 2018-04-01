package com.example.baciu.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    String email;
    String birthday;
    String friendsCount;
    URL profile_picture;
    ProgressDialog mDialog;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        final LoginButton login = (LoginButton) findViewById(R.id.login_button);

        login.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));

        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("Loading...");
                mDialog.show();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                             mDialog.dismiss();
                             getFacebookData(object);

                             login.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                                    intent.putExtra("AVATAR", profile_picture.toString());
                                    intent.putExtra("EMAIL", email);
                                    intent.putExtra("BIRTHDAY", birthday);
                                    intent.putExtra("TOTAL_FRIENDS", friendsCount);
                                    LoginActivity.this.startActivity(intent);
                                }
                             });
                    }
                });
                Bundle param = new Bundle();
                param.putString("fields", "id, email, birthday, friends");
                request.setParameters(param);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        if (AccessToken.getCurrentAccessToken() != null) {
            email = AccessToken.getCurrentAccessToken().getUserId();
        }
    }

    private void getFacebookData(JSONObject object) {
        try {
            profile_picture = new URL("https://graph.facebook.com/"+object.getString("id")+"/picture?width=100&height=100");
            email = object.getString("email");
            birthday = object.getString("birthday");
            friendsCount = object.getJSONObject("friends").getJSONObject("summary").getString("total_count");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
