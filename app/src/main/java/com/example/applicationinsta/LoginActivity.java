package com.example.applicationinsta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    Button btnSignUp;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(ParseUser.getCurrentUser() != null){
            goMainActivity();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick login button ");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick SignUp button ");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                SignUp(username, password);
            }
        });
    }

    private void SignUp(String username, String password) {
        ParseUser user = new ParseUser();
// Set core properties
        user.setUsername(username);
        user.setPassword(password);
// Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    //TODO: better error handling
                    Log.e(TAG, "Issue with SignUp", e);
                    Toast.makeText(LoginActivity.this, "Issue with SignUp", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    goMainActivity();
                    Toast.makeText(LoginActivity.this, "Succes", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }


    private void loginUser(String username, String password) {
                    Log.i(TAG, "Attempting to login user"+ username);
                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (e != null){
                                //TODO: better error handling
                                Log.e(TAG, "Issue with login", e);
                                Toast.makeText(LoginActivity.this, "Issue with login", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            goMainActivity();
                            Toast.makeText(LoginActivity.this, "Succes", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
    private void goMainActivity() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }



}
