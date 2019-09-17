package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class chooseapp extends AppCompatActivity {

    public void instagram (View v){
        Intent in = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(in);
    }
    public void twitter (View v){
        Intent in = new Intent(getApplicationContext(),twitter.class);
        startActivity(in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseapp);
    }
}
