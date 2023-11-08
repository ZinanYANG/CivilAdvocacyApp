package com.example.civiladvocacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PhotoActivity extends AppCompatActivity {
    private Toolbar MenuPhotoActivity;
    private TextView locationPhotoActivity ;
    private TextView OfficialNamePhotoActivity ;
    private TextView OfficeNamePhotoActivity ;


    private PhotoView photoPhotoActivity;
    private ImageView party_ImagePhotoActivity;
    private View backgroundViewPhotoActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

//        menu
        MenuPhotoActivity = findViewById(R.id.MenuPhotoActivity);
        MenuPhotoActivity.setTitle("Civil Advocacy");
        MenuPhotoActivity.setTitleTextColor(Color.WHITE);
//        location
        locationPhotoActivity = findViewById(R.id.locationPhotoActivity);
        OfficialNamePhotoActivity = findViewById(R.id.OfficialNamePhotoActivity);
        OfficeNamePhotoActivity = findViewById(R.id.OfficeNamePhotoActivity);

        photoPhotoActivity = findViewById(R.id.photoPhotoActivity);
        party_ImagePhotoActivity = findViewById(R.id.party_ImagePhotoActivity);
        backgroundViewPhotoActivity = findViewById(R.id.backgroundViewPhotoActivity);

//        get intent from officialActivity
        Intent intent = getIntent();
        Official selectedOfficial = (Official) intent.getSerializableExtra("selectedOfficial");


        //        location bar
        String lb = intent.getStringExtra("EXTRA_LOCATION");
        locationPhotoActivity.setText(lb);


        String officialName = selectedOfficial.getName();
        String officialTitle = selectedOfficial.getOfficeTitle();
        String photoUrl = selectedOfficial.getPhotoUrl();
        String party = selectedOfficial.getParty();

        OfficialNamePhotoActivity.setText(officialName);
        OfficeNamePhotoActivity.setText(officialTitle);
        if (photoUrl != null && photoUrl.startsWith("http:")) {
            photoUrl = photoUrl.replace("http:", "https:");
        }
        Picasso.get().load(photoUrl).into(photoPhotoActivity);

        if ("Democratic Party".equals(party)) {
            party_ImagePhotoActivity.setImageResource(R.drawable.dem_logo);
            party_ImagePhotoActivity.setOnClickListener(v -> {
                // Intent to open the Democratic Party website
                clickDem(null);
            });
            backgroundViewPhotoActivity.setBackgroundColor(Color.BLUE);
        } else if ("Republican Party".equals(party)) {
            party_ImagePhotoActivity.setImageResource(R.drawable.rep_logo);
            party_ImagePhotoActivity.setOnClickListener(v -> {
                // Intent to open the Republican Party website
                clickRep(null);
            });
            backgroundViewPhotoActivity.setBackgroundColor(Color.RED);
        } else {
            party_ImagePhotoActivity.setVisibility(View.GONE);
            backgroundViewPhotoActivity.setBackgroundColor(Color.BLACK);

        }
    }
    public void clickDem(View v) {
        String DemWebUrl = "https://democrats.org";
        Intent intent;
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(DemWebUrl));
        startActivity(intent);
    }
    public void clickRep(View v) {
        String RepWebUrl = "https://www.gop.com";
        Intent intent;
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(RepWebUrl));
        startActivity(intent);
    }
}