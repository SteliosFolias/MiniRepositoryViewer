package com.example.scouser.minirepositoryviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import io.realm.Realm;

public class Info extends AppCompatActivity {
    TextView textView6, textView8;
    ImageView imageView;
    int gdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        imageView = (ImageView) findViewById(R.id.imageView);
        textView6 = (TextView) findViewById(R.id.textView6);
        textView8 = (TextView) findViewById(R.id.textView8);
        Intent intent = getIntent();
        gdata=Integer.parseInt(intent.getStringExtra("data"));

        // Initialize Realm (just once per application)
        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        GitHub realmResultList = realm.where(GitHub.class).equalTo("id",gdata).findFirst();

        textView6.setText(realmResultList.getType());
        textView8.setText(realmResultList.getUrl());
        Picasso.with(getApplicationContext()).load(realmResultList.getAvatar()).resize(250, 250).into(imageView);

    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        int save=savedInstanceState.getInt("id");
        // Initialize Realm (just once per application)
        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        GitHub realmResultList = realm.where(GitHub.class).equalTo("id",save).findFirst();

        textView6.setText(realmResultList.getType());
        textView8.setText(realmResultList.getUrl());
        Picasso.with(getApplicationContext()).load(realmResultList.getAvatar()).resize(250, 250).into(imageView);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("id",gdata);
    }
}