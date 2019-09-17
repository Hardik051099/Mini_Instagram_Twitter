/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnKeyListener {
  EditText username,password;
  Button loginButton,signupButton;

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
  }
  public void userList () {
    Intent in = new Intent(getApplicationContext(),homescreen.class);
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
    setContentView(R.layout.activity_main);
    setTitle("MiniInstagram");
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
    /*
    //PARSE OBJECT CREATION

    ParseObject score = new ParseObject("Score");
    score.put("Username","David");
    score.put("Score",85);
    score.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if (e == null){
          Log.i("Score","Successfull");
        }
        else {
          e.printStackTrace();
          Log.i("Score","FAiled "+ e.toString());
        }
      }
    });

  //PARSE OBJECT QUERY

    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
    query.getInBackground("7PVRKWQMdb", new GetCallback<ParseObject>() {
      @Override
      public void done(ParseObject object, ParseException e) {
        if (e == null && object != null){
          Log.i("Username",object.getString("Username"));
          Log.i("Score",Integer.toString(object.getInt("Score")));
        }
      }
    });

  //PARSE OBJECT QUERY (to update data from existing objects)

    ParseObject parseObject = new ParseObject("New_Score");
    parseObject.put("Username","David");
    parseObject.put("Score","75");
     parseObject.put("Username","Lisa");
    parseObject.put("Score","56");
    parseObject.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if (e == null){
          Log.i("Parsing","Successful");
        }
        else {
          e.printStackTrace();
          Log.i("Parsing","Unsucessful "+e.toString());
        }
      }
    });
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");
    query.getInBackground("TrGZRsmCqk", new GetCallback<ParseObject>() {
      @Override
      public void done(ParseObject object, ParseException e) {
        if (e==null&&object!=null)
        {
          Log.i("old Username",object.getString("Username"));
          Log.i("old Password",object.getString("Password"));
          object.put("Username","Hardik05");
          object.put("Password","Hardik@05");
          object.saveInBackground();
          Log.i("New Username",object.getString("Username"));
          Log.i("New Password",object.getString("Password"));
        }
        else {

        }

      }
    });

    ParseQuery<ParseObject> query = ParseQuery.getQuery("New_Score");
    query.whereLessThan("Score",90);
    query.findInBackground(new FindCallback<ParseObject>()
    {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
       if(e == null  && objects != null ){
           for(ParseObject score : objects)
           {
             score.put("Score",score.getInt("Score")+10);
             score.saveInBackground();
             Log.i("adding","Successful");
             Log.i("Username",score.get("Username").toString());
             Log.i("Score",score.get("Score").toString());
           }
       }
       Log.i("Parsing","Successful");
      }
    });
    //Parse User Signup Function

    ParseUser user = new ParseUser();
    user.setUsername("Hardik");
    user.setPassword("Hardik@05");
    user.signUpInBackground(new SignUpCallback() {
      @Override
      public void done(ParseException e) {
       if (e==null){
         Log.i("Signup","Successful");
       }

      }
    });

    //Parse User Login Function (No object is created here)

    ParseUser.logInInBackground("Hardik", "Hardik@05", new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException e) {
        if (e==null&&user!=null){
          Log.i("Log in","Succeful");
        }
        else{
          e.printStackTrace();
          Log.i("Log in","Unsucceful "+e.toString());
        }
      }
    });
    //To Log out current user

    ParseUser.logOut();

  //To check user is already logged in or not

  if (ParseUser.getCurrentUser() != null){
    Log.i("Logged in","YES Username:- "+ParseUser.getCurrentUser().getUsername());
  }
  else {
    Log.i("Logged in","NO");
  }
  */

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