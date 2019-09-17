package com.parse.starter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class twitterfollowers extends AppCompatActivity {
    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> userlist;
    public void Logout (){
        ParseUser.logOut();
        Toast.makeText(this,"LogOut Successful",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
    public void getTweets(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedimages = data.getData();

        if(requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedimages);
                //imageView.setImageBitmap(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream(); //creating bytearray for sending image source code to parse
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                byte[] byteArray = stream.toByteArray(); //storing source code of image in byteArray
                ParseFile file = new ParseFile("image.png",byteArray); //creating parse file
                ParseObject parseObject = new ParseObject("Image");
                parseObject.put("Image",file);
                parseObject.put("Username",ParseUser.getCurrentUser().getUsername());
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            Toast.makeText(getApplicationContext(),"Image has been Shared Successfully",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Image uploading has been Failed!!",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.share_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.share){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
            else {
                getTweets();
            }

        }
        else if(item.getItemId() == R.id.Logout) {
            Logout();
        }


        return super.onOptionsItemSelected(item);
    }
*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitterfollowers);
        setTitle("Followers list");
        userlist = new ArrayList<>();
        userlist.add("Hardik");
        userlist.add("Arekar");
        listView = findViewById(R.id.userlist);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_checked,userlist);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView = (CheckedTextView) view;
                if(checkedTextView.isChecked()){
                    Log.i("info","Checked");
                }
                else {
                    Log.i("info","Unchecked");
                }
            }
        });
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.addAscendingOrder("username");

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e==null){
                    if(objects.size()>0) {
                        for (ParseUser user : objects) {
                            userlist.add(user.getUsername());
                        }
                        listView.setAdapter(arrayAdapter);
                    }
                }
                else {
                    e.printStackTrace();
                }
            }
        });

    }
}
