package com.example.civiladvocacyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class AboutActivity extends AppCompatActivity {

    private Toolbar MenuAboutActivity;
    private TextView ApiAttributionAboutActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //        menu
        MenuAboutActivity = findViewById(R.id.MenuAboutActivity);
        MenuAboutActivity.setTitle("Civil Advocacy");
        MenuAboutActivity.setTitleTextColor(Color.WHITE);
        Objects.requireNonNull(MenuAboutActivity.getOverflowIcon()).setTint(Color.WHITE);
        setSupportActionBar(MenuAboutActivity);

//        Api attribution
        ApiAttributionAboutActivity = findViewById(R.id.ApiAttributionAboutActivity);
        SpannableString ss = new SpannableString("Google Civic Information API");
        ss.setSpan(new UnderlineSpan(), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ApiAttributionAboutActivity.setText(ss);
    }
    public void ClickApiAttributionAboutActivity(View v){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://developers.google.com/civic-information/"));
        startActivity(browserIntent);
    }
}