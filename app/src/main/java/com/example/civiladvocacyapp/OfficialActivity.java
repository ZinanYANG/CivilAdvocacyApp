package com.example.civiladvocacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class OfficialActivity extends AppCompatActivity {

    private Toolbar MenuOfficialActivity;

    //    location bar
    private TextView locationOfficialActivity;
    private TextView officialNameOfficialActivity;
    private TextView officeNameOfficialActivity;
    private TextView party_TextOfficialActivity;
    private TextView addressOfficialActivity;
    private TextView addressUnderlineOfficialActivity;
    private TextView phoneOfficialActivity;
    private TextView phoneUnderlineOfficialActivity;
    private TextView emailOfficialActivity;
    private TextView emailUnderlineOfficialActivity;
    private TextView websiteOfficialActivity;
    private TextView websiteUnderlineOfficialActivity;

    private ImageView photoOfficialActivity;
    private ImageView facebookOfficialActivity;
    private ImageView youTubeOfficialActivity;
    private ImageView twitterOfficialActivity;
    private ImageView party_ImageOfficialActivity;
    //    background color
    private View backgroundViewOfficialActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);

        //        menu
        MenuOfficialActivity = findViewById(R.id.MenuOfficialActivity);
        MenuOfficialActivity.setTitle("Civil Advocacy");
        MenuOfficialActivity.setTitleTextColor(Color.WHITE);
//        for intent
        locationOfficialActivity = findViewById(R.id.locationOfficialActivity);
        officialNameOfficialActivity = findViewById(R.id.officialNameOfficialActivity);
        officeNameOfficialActivity = findViewById(R.id.officeNameOfficialActivity);
        party_TextOfficialActivity = findViewById(R.id.party_TextOfficialActivity);

        photoOfficialActivity = findViewById(R.id.photoOfficialActivity);
        facebookOfficialActivity = findViewById(R.id.facebookOfficialActivity);
        youTubeOfficialActivity = findViewById(R.id.youTubeOfficialActivity);
        twitterOfficialActivity = findViewById(R.id.twitterOfficialActivity);
        party_ImageOfficialActivity = findViewById(R.id.party_ImageOfficialActivity);

        addressOfficialActivity = findViewById(R.id.addressOfficialActivity);
        addressUnderlineOfficialActivity = findViewById(R.id.addressUnderlineOfficialActivity);
        phoneOfficialActivity = findViewById(R.id.phoneOfficialActivity);
        phoneUnderlineOfficialActivity = findViewById(R.id.phoneUnderlineOfficialActivity);
        emailOfficialActivity = findViewById(R.id.emailOfficialActivity);
        emailUnderlineOfficialActivity = findViewById(R.id.emailUnderlineOfficialActivity);
        websiteOfficialActivity = findViewById(R.id.websiteOfficialActivity);
        websiteUnderlineOfficialActivity = findViewById(R.id.websiteUnderlineOfficialActivity);
        backgroundViewOfficialActivity = findViewById(R.id.backgroundViewOfficialActivity);
//        data
        Intent intent = getIntent();
//        location bar
        String lb = intent.getStringExtra("EXTRA_LOCATION");
        locationOfficialActivity.setText(lb);
        String officialName = intent.getStringExtra("OFFICIAL_NAME");
        String officialTitle = intent.getStringExtra("OFFICIAL_TITLE");
        String party = intent.getStringExtra("Party");
        if ("Democratic Party".equals(party)) {
            party_ImageOfficialActivity.setImageResource(R.drawable.dem_logo);
            party_ImageOfficialActivity.setOnClickListener(v -> {
                clickDem(null);
            });
            backgroundViewOfficialActivity.setBackgroundColor(Color.BLUE);

        } else if ("Republican Party".equals(party)) {
            party_ImageOfficialActivity.setImageResource(R.drawable.rep_logo);
            party_ImageOfficialActivity.setOnClickListener(v -> {
                clickRep(null);
            });
            backgroundViewOfficialActivity.setBackgroundColor(Color.RED);
        } else {
            party_ImageOfficialActivity.setVisibility(View.GONE);
            backgroundViewOfficialActivity.setBackgroundColor(Color.BLACK);
        }

        String Address = intent.getStringExtra("Address");
        String City = intent.getStringExtra("City");
        String State = intent.getStringExtra("State");
        String Zip = intent.getStringExtra("Zip");
        String address = Address + ", " + City + ", " + State + " " + Zip;

        String phone = intent.getStringExtra("Phone");
        String email = intent.getStringExtra("Email");
        String website = intent.getStringExtra("Website");
        String photoUrl = intent.getStringExtra("PhotoUrl");
        officialNameOfficialActivity.setText(officialName);
        if (officialTitle != null && !officialTitle.isEmpty()) {
            officeNameOfficialActivity.setText(officialTitle);
            officeNameOfficialActivity.setVisibility(View.VISIBLE);
        } else {
            officeNameOfficialActivity.setVisibility(View.GONE);
        }
        if (party != null && !party.isEmpty()) {
            party_TextOfficialActivity.setText(party);
            party_TextOfficialActivity.setVisibility(View.VISIBLE);
        } else {
            party_TextOfficialActivity.setVisibility(View.GONE);
        }
        if (phone != null && !phone.isEmpty()) {
            phoneOfficialActivity.setText("Phone:");
            phoneUnderlineOfficialActivity.setText(phone);
            phoneOfficialActivity.setVisibility(View.VISIBLE);
            phoneUnderlineOfficialActivity.setVisibility(View.VISIBLE);
            phoneUnderlineOfficialActivity.setLinkTextColor(Color.WHITE);
            Linkify.addLinks(phoneUnderlineOfficialActivity, Linkify.ALL);
        } else {
            phoneOfficialActivity.setVisibility(View.GONE);
            phoneUnderlineOfficialActivity.setVisibility(View.GONE);
        }
        if (email != null && !email.isEmpty()) {
            emailOfficialActivity.setText("Email:");
            emailUnderlineOfficialActivity.setText(email);
            emailOfficialActivity.setVisibility(View.VISIBLE);
            emailUnderlineOfficialActivity.setVisibility(View.VISIBLE);
            emailUnderlineOfficialActivity.setLinkTextColor(Color.WHITE);
            Linkify.addLinks(emailUnderlineOfficialActivity, Linkify.ALL);

        } else {
            emailOfficialActivity.setVisibility(View.GONE);
            emailUnderlineOfficialActivity.setVisibility(View.GONE);
        }

        if (website != null && !website.isEmpty()) {
            websiteOfficialActivity.setText("Website:");
            websiteUnderlineOfficialActivity.setText(website);
            websiteOfficialActivity.setVisibility(View.VISIBLE);
            websiteUnderlineOfficialActivity.setVisibility(View.VISIBLE);
            websiteUnderlineOfficialActivity.setLinkTextColor(Color.WHITE);
            Linkify.addLinks(websiteUnderlineOfficialActivity, Linkify.ALL);
        } else {
            websiteOfficialActivity.setVisibility(View.GONE);
            websiteUnderlineOfficialActivity.setVisibility(View.GONE);
        }

//        for channels intent
        Official official = (Official) getIntent().getSerializableExtra("selectedOfficial");
//        photoUrl official activity
        if (photoUrl != null && photoUrl.startsWith("http:")) {
            photoUrl = photoUrl.replace("http:", "https:");
        }

        if (photoUrl != null && !photoUrl.trim().isEmpty()) {
            photoOfficialActivity.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Picasso.get()
                    .load(photoUrl)
                    .error(R.drawable.brokenimage)
                    .into(photoOfficialActivity);
            photoOfficialActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OfficialActivity.this, PhotoActivity.class);
                    //        location bar
                    String lb = locationOfficialActivity.getText().toString();
                    intent.putExtra("EXTRA_LOCATION", lb);
                    intent.putExtra("selectedOfficial", official);
                    Toast.makeText(OfficialActivity.this, official.getOfficeTitle(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });

        } else {
            photoOfficialActivity.setImageResource(R.drawable.missing);
        }

        if (address != null && !address.trim().isEmpty()) {
            addressOfficialActivity.setText("Address:");
            addressUnderlineOfficialActivity.setText(address);

            addressOfficialActivity.setVisibility(View.VISIBLE);
            addressUnderlineOfficialActivity.setVisibility(View.VISIBLE);
            addressUnderlineOfficialActivity.setLinkTextColor(Color.WHITE);
            Linkify.addLinks(addressUnderlineOfficialActivity, Linkify.ALL);

        } else {
            addressOfficialActivity.setVisibility(View.GONE);
            addressUnderlineOfficialActivity.setVisibility(View.GONE);
        }
        if (official != null) {
            List<Official.SocialChannel> channels = official.getChannels();

//            set all channels gone initially
            facebookOfficialActivity.setVisibility(View.GONE);
            twitterOfficialActivity.setVisibility(View.GONE);
            youTubeOfficialActivity.setVisibility(View.GONE);

            if (channels != null && !channels.isEmpty()) {
                // The official has social channels, process them accordingly
                for (Official.SocialChannel channel : channels) {
                    // Access the type and id of each channel
                    String type = channel.getType();
                    String id = channel.getId();
                    if (type.equalsIgnoreCase("Facebook")) {
                        facebookOfficialActivity.setVisibility(View.VISIBLE);
                        facebookOfficialActivity.setOnClickListener(v -> {
                            clickFacebook(id);
                        });
                    } else if (type.equalsIgnoreCase("Twitter")) {
                        twitterOfficialActivity.setVisibility(View.VISIBLE);
                        twitterOfficialActivity.setOnClickListener(v -> {
                            clickTwitter(id);
                        });
                    } else if (type.equalsIgnoreCase("YouTube")) {
                        youTubeOfficialActivity.setVisibility(View.VISIBLE);
                        youTubeOfficialActivity.setOnClickListener(v -> {
                            clickYouTube(id);
                        });
                    }
                }

            } else {
                Toast.makeText(this, "no channels found", Toast.LENGTH_SHORT).show();
            }
        }

    }

    //    facebook
    public void clickFacebook(String id) {
        // You need the FB user's id for the url
        String FACEBOOK_URL = "https://www.facebook.com/" + id;

        Intent intent;

        // Check if FB is installed, if not we'll use the browser
        if (isPackageInstalled("com.facebook.katana")) {
            String urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlToUse));
        } else {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL));
        }
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles ACTION_VIEW (fb/https) intents");
        }
    }

    //    YouTube
    public void clickYouTube(String name) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.google.android.youtube");
        intent.setData(Uri.parse("https://www.youtube.com/watch/" + name));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles ACTION_VIEW (youtube/https) intents");
        }
    }

    //    Twitter
    public void clickTwitter(String user) {
        String twitterAppUrl = "twitter://user?screen_name=" + user;
        String twitterWebUrl = "https://twitter.com/" + user;

        Intent intent;
        // Check if Twitter is installed, if not we'll use the browser
        if (isPackageInstalled("com.twitter.android")) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterAppUrl));
        } else {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterWebUrl));
        }

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles ACTION_VIEW (twitter/https) intents");
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
    //    facebook twitter YouTube
    public boolean isPackageInstalled(String packageName) {
        try {
            return getPackageManager().getApplicationInfo(packageName, 0).enabled;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    
    //    facebook twitter YouTube
    private void makeErrorAlert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(msg);
        builder.setTitle("No App Found");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}