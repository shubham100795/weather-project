package com.example.android.weather_project;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LoaderManager.LoaderCallbacks<condition> {

    private final String LOG_TAG =MainActivity.class.getName();
    public static String WEATHER_REQUEST_URL = "http://api.wunderground.com/api/a36dfde35fd0f46f/geolookup/astronomy/forecast10day/conditions/q/";//x,y.json to be added
    private GoogleApiClient mGoogleApiClient;
    private static final int WEATHER_LOADER_ID = 1;
    private Location mLastLocation;
    private Boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_activity);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


    }

    @Override
    public Loader<condition> onCreateLoader(int id, Bundle args) {

            return new WeatherLoader(this, WEATHER_REQUEST_URL);
    }
    @Override
    public void onLoadFinished(Loader<condition> loader, condition current_condition) {

        if (current_condition != null) {
            setContentView(R.layout.activity_main);
            TextView temp=(TextView)findViewById(R.id.temperature);
            temp.setText(Double.toString(current_condition.gettemperature())+" \u2103");
            TextView city=(TextView)findViewById(R.id.city);
            city.setText(current_condition.getcity());
            TextView state=(TextView)findViewById(R.id.state);
            state.setText(current_condition.getstate());
            TextView country=(TextView)findViewById(R.id.country);
            country.setText(current_condition.getcountry());
            TextView weather=(TextView)findViewById(R.id.weather);
            weather.setText(current_condition.getweather());
            TextView humidity=(TextView)findViewById(R.id.humidity);
            humidity.setText(current_condition.gethumidity());
            TextView wind=(TextView)findViewById(R.id.wind);
            wind.setText(current_condition.getwind()+" kmp");
            TextView sunrise=(TextView)findViewById(R.id.sunrise);
            sunrise.setText(current_condition.getsunrise()+" AM");
            TextView sunset=(TextView)findViewById(R.id.sunset);
            sunset.setText(current_condition.getsunset()+" PM");
            AQuery aq=new AQuery(this); // intsialze aquery
            aq.id(R.id.imageview).image(current_condition.getimage());
            Log.i(LOG_TAG,current_condition.getimage());
        }
        else
        {
            TextView view=(TextView)findViewById(R.id.text_view);
            view.setText("SOMETHING WENT WRONG");
        }
        Log.i(LOG_TAG,"on load finished method");
    }

    @Override
    public void onLoaderReset(Loader<condition> loader) {
       //
    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            if (flag == true) {
                WEATHER_REQUEST_URL = WEATHER_REQUEST_URL + mLastLocation.getLatitude() + "," + mLastLocation.getLongitude() + ".json";
                flag=false;
            }
            Log.i(LOG_TAG, WEATHER_REQUEST_URL);
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                getSupportLoaderManager().initLoader(WEATHER_LOADER_ID, null, this);
            }
        }
            else
            {
                ProgressBar spin=(ProgressBar)findViewById(R.id.loading_spinner);
                spin.setVisibility(View.GONE);
                TextView view=(TextView)findViewById(R.id.text_view);
                view.setText("NO INTERNET CONNECTION");
            }
        }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG,"in onpause");
        setContentView(R.layout.blank_activity);
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOG_TAG,"connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(LOG_TAG,"connection failed");
    }

    public void forecast(View view)
    {
        Log.i(LOG_TAG,"in onclick");
        Intent intent=new Intent(this,forecast.class);//NumbersActivity is the activity name
        startActivity(intent);
    }
}
