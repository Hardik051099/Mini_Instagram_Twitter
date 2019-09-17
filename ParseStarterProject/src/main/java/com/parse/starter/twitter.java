package com.parse.starter;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class twitter extends AppCompatActivity implements View.OnClickListener,View.OnKeyListener{
    EditText username,password;
    Button loginButton,signupButton;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void userList () {
        Intent in = new Intent(getApplicationContext(),twitterfollowers.class);
        startActivity(in);
    }
    public void Login (View view) {
        if (ParseUser.getCurrentUser() != null) {
            Toast.makeText(this, "Already Logged in!", Toast.LENGTH_LONG).show();
        }
        else {
            if (username.getText().toString().matches("") || password.getText().toString().matches("")) {
                Toast.makeText(this, "Please fill all the inputs", Toast.LENGTH_LONG).show();
            } else {
                ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null && user != null) {
                            Log.i("Log in", "Successful");
                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                            userList();

                        } else {
                            e.printStackTrace();
                            Log.i("Log in", "Unsuccessful " + e.toString());
                            Toast.makeText(getApplicationContext(), "Incorrect Username or Password", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }
    public void Signup (View view)
    {
        if(username.getText().toString().matches("") || password.getText().toString().matches(""))
        {
            Toast.makeText(this,"Please fill all the inputs",Toast.LENGTH_LONG).show();
        }
        else {
            ParseUser user = new ParseUser();
            user.setUsername(username.getText().toString());
            user.setPassword(password.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.i("Signup", "Successful");
                        Toast.makeText(getApplicationContext(),"Sign up Successful",Toast.LENGTH_LONG).show();
                        userList();
                    }
                    else {
                        Log.i("Signup",e.toString());
                        Toast.makeText(getApplicationContext(),"Account already exists for this username.",Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);
        setTitle("MiniTwitter");
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        signupButton = findViewById(R.id.signup);
        password.setOnKeyListener(this);
        ImageView img = findViewById(R.id.imageView2);
        ConstraintLayout backgroundLayout = findViewById(R.id.backgroundLayout);
        img.setOnClickListener(this);
        backgroundLayout.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            userList();
        }
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageView2 || v.getId() == R.id.backgroundLayout){
            //Keyboard Management
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }
    }
    //to login via Enter key from keyboard
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN )
        {
            Login(v);

        }
        return false;
    }
}
