package com.thegavners.parseservermessanger;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

// Copyright Carlos Mbendera

public class Sign extends AppCompatActivity {

    ParseUser user;
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        Parse.enableLocalDatastore(this);

        // Add your initialization code here

        // Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
        // .applicationId()
        // .clientKey()
        // .server()
        // .build());


        //ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);


    }


    public void signUp (View view) {


        if (username.getText().toString().matches("")
                || password.getText().toString().matches("")) {
            Toast.makeText
                    (this, "You need to fill in all the fields",
                            Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "Please Wait, while we sign you up.", Toast.LENGTH_SHORT).show();

             user = new ParseUser();

            user.setUsername(username.getText().toString());
            user.setPassword(password.getText().toString());
        }
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    Log.i("Sign Up", "Successful");
                    Toast.makeText
                            (getApplicationContext(), "Welcome to Gavn " + ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Chats.class));

                } else {

                    Log.i("Sign Up", "Sign Up failed due to: "
                            + e.toString()
                            +"." );
                    Toast.makeText
                            (getApplicationContext(), "Please try again later. " ,Toast.LENGTH_SHORT).show();

                }
            }
        });


    }


    public void signIn(View view){
        if (username.getText().toString().matches("") || password.getText().toString().matches("")) {

            Toast.makeText(this, "Both your Username and Password are required.", Toast.LENGTH_SHORT).show();

        } else {
            ParseUser.logInInBackground(
                    username.getText().toString(),
                    password.getText().toString(),
                    new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (e == null) {
                                Log.i("LogIn", "Success");
                                Toast.makeText(getApplicationContext(), "Welcome Back "+ParseUser.getCurrentUser().getUsername() , Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Chats.class));
                            } else {
                                Log.i("LogIn", "Failed due to error " + e.toString());
                                Toast.makeText(getApplicationContext(), "Log In failed because of "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }


    }
}
