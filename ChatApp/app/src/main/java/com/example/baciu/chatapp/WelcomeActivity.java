package com.example.baciu.chatapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    TextView email;
    TextView birthday;
    TextView friendsCount;
    ImageView mAvatar;
    private static final int REQUEST_LOCATION = 1;
    private Button locationButton;
    private LocationManager locationManager;
    private TextView locationText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAvatar = (ImageView) findViewById(R.id.profile_picture);
        email = (TextView) findViewById(R.id.email);
        birthday = (TextView) findViewById(R.id.birthday);
        friendsCount = (TextView) findViewById(R.id.total_friends);
        locationButton = (Button) findViewById(R.id.location_button);
        locationText = (TextView) findViewById(R.id.current_location);

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (! locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    AlertMessageNoGps();
                } else {
                    getLocation();
                }
            }
        });

        Intent intent = getIntent();

        String txtAvatar = intent.getStringExtra("AVATAR");
        Picasso.with(this).load(txtAvatar).into(mAvatar);

        String txtEmail = intent.getStringExtra("EMAIL");
        email.setText("E-mail: "+txtEmail);

        String txtBirthday = intent.getStringExtra("BIRTHDAY");
        birthday.setText("Birthday: "+txtBirthday);

        String txtFriends = intent.getStringExtra("TOTAL_FRIENDS");
        friendsCount.setText("You have "+ txtFriends+ " friends on Facebook");
    }

    private void getLocation() {
        if ((ActivityCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(WelcomeActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location currentLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (currentLocation != null) {
                String latitude = String.valueOf(currentLocation.getLatitude());
                String longitude = String.valueOf(currentLocation.getLongitude());
                locationText.setText("Your current position is: \nLongitude: " + longitude + "\n"
                                    +" Latitude: " + latitude);
            } else {
                Toast.makeText(this,"Unable to find your current location", Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void AlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please, turn on your GPS")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
    }
}
