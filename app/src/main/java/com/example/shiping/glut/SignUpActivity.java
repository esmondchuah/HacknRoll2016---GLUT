package com.example.shiping.glut;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    String mEmail;
    String mPassword;
    String mName;
    String mPhoneNumber;
    String gender;
    String location;
    Firebase ref;
    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_sign_up);
        ref = new Firebase("https://amber-fire-4138.firebaseio.com");
    }

    public void signUp(View v) {
        mEmail = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
        mPassword = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();
        mName = ((EditText) findViewById(R.id.nameEditText)).getText().toString();
        MainActivity.name = mName;
        mPhoneNumber = ((EditText) findViewById(R.id.phoneNumberEditText)).getText().toString();

        ref.createUser(mEmail, mPassword, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Toast.makeText(getApplicationContext(), "Successfully created user account with uid: " + result.get("uid"), Toast.LENGTH_SHORT).show();
                Firebase userRef = ref.child("users").child(result.get("uid").toString());
                userRef.setValue(new User(mName, mPhoneNumber));
                new GetRequest().execute();
                Intent confirmIntent = new Intent(getApplicationContext(), NumberAuthenication.class);
                startActivity(confirmIntent);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "Account creation failed", Toast.LENGTH_SHORT).show();// there was an error
            }
        });
    }

    public boolean checkLocation(String location) {
        if (location.equals("NUS Engineering Canteen"))
            return true;
        else return false;
    }
}

class GetRequest extends AsyncTask<Void, Integer, Void> {
    protected Void doInBackground(Void... urls) {
        URL url;
        HttpURLConnection connection = null;
        try {
// Create connection
            url = new URL("https://maker.ifttt.com/trigger/account_created/with/key/fMXbjFJYsd_qUG3_brSEWUEGc21J80cfEEd09dfcZij");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
// Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.flush();
            wr.close();
// Get Response
        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

}