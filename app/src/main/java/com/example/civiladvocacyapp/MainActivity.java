package com.example.civiladvocacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener {
    private static final String TAG = "MainActivity";
    private Toolbar MenuMainActivity;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int LOCATION_REQUEST = 111;
    private String locationEntered;
    private String currentZipCode;
    private static String locationString = "Unspecified Location";
    private int fetchDataCalls = 0; // location track
    private TextView locationMainActivity;
    private final List<Official> OfficialList = new ArrayList<>();  // Main content is here

    private RecyclerView recyclerView; // Layout's recyclerview

    private OfficialAdapter officialAdapter; // Data to recyclerview adapter

    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started");
        setContentView(R.layout.activity_main);
//        menu
        MenuMainActivity = findViewById(R.id.MenuMainActivity);
        MenuMainActivity.setTitle("Civil Advocacy");
        MenuMainActivity.setTitleTextColor(Color.WHITE);
        Objects.requireNonNull(MenuMainActivity.getOverflowIcon()).setTint(Color.WHITE);
        setSupportActionBar(MenuMainActivity);

//        recyclerView

        recyclerView = findViewById(R.id.recycler);
        officialAdapter = new OfficialAdapter(OfficialList, this);
        recyclerView.setAdapter(officialAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        //        location
        locationMainActivity = findViewById(R.id.locationMainActivity);
        mFusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);
        determineLocation();

//        make sure if fetchData fails then this block of code still got executed
        // Initiate the network request to fetch data
        String apiKey = "AIzaSyBfZnbARfrVwn-g5b7kO4NDJcWgARq7W-o";
//        String key2 = "AIzaSyAMBK_P4CjOczRVWweFdmOkbT-mSfg3ur4";
        String zipCode = "chicago"; // zipCode or "Chicago"
        String url = buildUrl(apiKey, zipCode);
//        fetchData(url);
    }

//    location
    private void showLocationDialog() {
        Log.d("DialogTest", "Attempting to show dialog");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Location");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Enter address, city, or zip code");
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            String locationInput = input.getText().toString();

            if (!locationInput.isEmpty() && isNetworkAvailable()) {
//                fetchDataBasedOnLocation(userSpecifiedLocation);
                // Handle the location input
                handleLocationInput(locationInput);

                // Initiate the network request to fetch data
                String apiKey = "AIzaSyBfZnbARfrVwn-g5b7kO4NDJcWgARq7W-o";
                String zipCode = locationInput; // or "Chicago" for city
                Log.d("DialogTest", zipCode);
                String url = buildUrl(apiKey, zipCode);

                fetchData(url);

            } else {
                if (!isNetworkAvailable()) {
                    Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "No location entered", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
        Log.d("DialogTest", "Dialog should now be visible");
    }

//    error handle
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting());
    }
    //    location
    private void handleLocationInput(String locationInput) {
        locationMainActivity.setText(locationInput);
    }

//    From OnClickListener
//    for the recyclerView implementation
//    onClick override has to be implemented on mainActivity from adapter  => error
    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildLayoutPosition(v);
        Official selectedOfficial = OfficialList.get(pos);
        Intent intent = new Intent(MainActivity.this, OfficialActivity.class);
//        location bar
        String lb = locationMainActivity.getText().toString();
        intent.putExtra("EXTRA_LOCATION", lb);
        // Pass data to the next activity using intent.putExtra()
        intent.putExtra("OFFICIAL_NAME", selectedOfficial.getName());
        intent.putExtra("OFFICIAL_TITLE", selectedOfficial.getOfficeTitle());
        intent.putExtra("Party", selectedOfficial.getParty());

        intent.putExtra("Address", selectedOfficial.getAddress());
        intent.putExtra("City", selectedOfficial.getCity());
        intent.putExtra("State", selectedOfficial.getState());
        intent.putExtra("Zip", selectedOfficial.getZip());
        intent.putExtra("Phone", selectedOfficial.getPhoneNumber());
        intent.putExtra("Email", selectedOfficial.getEmail());
        intent.putExtra("Website", selectedOfficial.getWebsiteUrl());
        intent.putExtra("PhotoUrl", selectedOfficial.getPhotoUrl());
//        for channels intent
        intent.putExtra("selectedOfficial", selectedOfficial);
        startActivity(intent);
    }


//    for the recyclerView implementation
//    From OnLongClickListener
//    onClick override has to be implemented on mainActivity from adapter  => error

    @Override
    public boolean onLongClick(View v) {  // long click listener called by ViewHolder long clicks
        int pos = recyclerView.getChildLayoutPosition(v);
        return true;
    }

    //    for the menue and toolBar
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    //    for the menu and toolBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.aboutMainActivity) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.locationMainActivity) {
            OfficialList.clear();
            showLocationDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
//    location
    private void determineLocation() {
        // Check perm - if not then start the  request and return
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some situations this can be null.
                    if (location != null) {
                        locationString = getPlace(location);
                        locationMainActivity.setText(locationString);

                        // Initiate the network request to fetch data
                        String apiKey = "AIzaSyBfZnbARfrVwn-g5b7kO4NDJcWgARq7W-o";
                        currentZipCode = "";
                        int lastSpaceIndex = locationString.lastIndexOf(' ');
                        if (lastSpaceIndex != -1) {
                            currentZipCode = locationString.substring(lastSpaceIndex + 1);
                        }

                        Toast.makeText(this, currentZipCode, Toast.LENGTH_SHORT).show();
                        String url = buildUrl(apiKey, currentZipCode);
                        Log.d(TAG, "determineLocation: " + url);
                        fetchData(url);
                    }
                })
                .addOnFailureListener(this, e ->
                        Toast.makeText(MainActivity.this,
                                e.getMessage(), Toast.LENGTH_LONG).show());
    }
    //    location
    private String getPlace(Location loc) {
        StringBuilder sb = new StringBuilder();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                String addr = addresses.get(0).getAddressLine(0);

//                 remove USA characters
                if (addr.length() >= 5) {
                    addr = addr.substring(0, addr.length() - 5);
                }
                sb.append(String.format(
                        Locale.getDefault(),
                        "%s%n%n",
                        addr
                ));
            } else {
                sb.append(getString(R.string.cannot_determine_location));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    //    location
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_REQUEST) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    determineLocation();
                } else {
                    locationMainActivity.setText(R.string.deniedText);
                }
            }
        }
    }
    //    volley search
    private String buildUrl(String apiKey, String address) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("www.googleapis.com")
                .appendPath("civicinfo")
                .appendPath("v2")
                .appendPath("representatives")
                .appendQueryParameter("key", apiKey)
                .appendQueryParameter("address", address);

        String constructedUrl = builder.build().toString();
        // Log the URL to check if it's correct
        Log.d("URL_CHECK", "Constructed URL: " + constructedUrl);
        return builder.build().toString();
    }


//volley search
    public void fetchData(String url) {
        Log.d("NETWORK_DEBUG", "Initiating fetch data from URL: " + url);
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d("NETWORK_DEBUG", "Received response from server");
                    parseJSON(response);
                },
                error -> {
                    // Handle the error
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        String json = new String(error.networkResponse.data);
                        try {
                            JSONObject obj = new JSONObject(json);
                            if (obj.has("error")) {
                                JSONObject err = obj.getJSONObject("error");
                                if (err.has("message") && err.getString("message").equals("Failed to parse address")) {
                                    Toast.makeText(this, "no location found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // If the error is a timeout or no connection error, log it as such
                    if (error instanceof TimeoutError) {
                        Log.e("NETWORK_ERROR", "Timeout error - the server took too long to respond");
                    } else if (error instanceof NoConnectionError) {
                        Log.e("NETWORK_ERROR", "No connection error - check the device's network connection");
                    }
                    // If the error is an AuthFailureError, it means there was an authentication issue
                    if (error instanceof AuthFailureError) {
                        Log.e("NETWORK_ERROR", "Authentication failure - check your API keys and user credentials");
                    }
                }
        );
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
    //    volley search
    private void parseJSON(JSONObject response) {
        try {
            Log.d("JSON_PARSING", "Starting JSON parsing");
            fetchDataCalls++; // location track
            JSONObject normalizedInput = response.getJSONObject("normalizedInput");
            JSONArray officesArray = response.getJSONArray("offices");
            JSONArray officialsArray = response.getJSONArray("officials");
            for (int i = 0; i < officesArray.length(); i++) {
                JSONObject office = officesArray.getJSONObject(i);
                String officeTitle = office.getString("name");

                JSONArray officialIndices = office.getJSONArray("officialIndices");
                for (int j = 0; j < officialIndices.length(); j++) {
                    int officialIndex = officialIndices.getInt(j);
                    JSONObject officialObj = officialsArray.getJSONObject(officialIndex);
                    String name = "Unknown"; // Default value

                    if (officialObj.has("name")) {
                        name = officialObj.getString("name");
                    }
                    String party = "Unknown"; // Default value

                    if (officialObj.has("party")) {
                        party = officialObj.getString("party");
                    }
                    String phoneNumber = null;
                    if (officialObj.has("phones")) {
                        phoneNumber = officialObj.getJSONArray("phones").optString(0, null);
                    }
                    String websiteUrl = null;

                    if (officialObj.has("urls")) {
                        websiteUrl = officialObj.getJSONArray("urls").optString(0, null);
                    }
                    String email = null;
                    if (officialObj.has("emails")) {
                        email = officialObj.getJSONArray("emails").optString(0, null);
                    }
                    String photoUrl = officialObj.optString("photoUrl", null);
                    if (photoUrl == null) {
                        Log.d("DEBUG_PHOTO_URL", "Photo URL for official: " + name + " is: " + photoUrl);
                    }
                    String address = null;
                    String city = null;
                    String state = null;
                    String zip = null;

                    if (officialObj.has("address")) {
                        JSONObject addressObj = officialObj.getJSONArray("address").getJSONObject(0);
                        address = addressObj.optString("line1", "") +
                                addressObj.optString("line2", "") +
                                addressObj.optString("line3", "");
                        city = addressObj.getString("city");
                        state = addressObj.getString("state");
                        zip = addressObj.getString("zip");
                    }
                    if (fetchDataCalls > 1) {
                        if(city!=null&&!city.isEmpty()
                                &&state!=null&&!state.isEmpty()
                                &&zip!=null&&!zip.isEmpty()){}
                            locationMainActivity.setText(city + ", " + state + " " + zip);
                    }
                    List<Official.SocialChannel> channels = new ArrayList<>();
                    if (officialObj.has("channels")) {
                        JSONArray channelsArray = officialObj.getJSONArray("channels");
                        for (int k = 0; k < channelsArray.length(); k++) {
                            JSONObject channelObj = channelsArray.getJSONObject(k);
                            String type = channelObj.getString("type");
                            String id = channelObj.getString("id");
                            channels.add(new Official.SocialChannel(type, id));
                        }
                    }
                    // Create an Official object
                    Official official = new Official(name, officeTitle, address, city, state, zip, party, phoneNumber, websiteUrl, email, photoUrl, channels);
                    OfficialList.add(official);
                    officialAdapter.notifyDataSetChanged();
                }
            }
            Log.d("JSON_PARSING", "Completed JSON parsing");
        } catch (JSONException e) {
            Log.e("JSON_PARSING_ERROR", "Error in parsing: " + e.toString());
            e.printStackTrace();
        }
    }
}