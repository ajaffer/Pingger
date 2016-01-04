package com.navnex.pingger;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class ScrollingActivity extends AppCompatActivity {

    LocationManager locationManager;

    // Define a listener that responds to location updates
    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            new HttpRequestTask().execute(location);
//            ping(location.getLatitude(), location.getLongitude());
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {}
    };

    String locationProvider = LocationManager.NETWORK_PROVIDER;
    // Or, use GPS location data:
    // String locationProvider = LocationManager.GPS_PROVIDER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
//        ping(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
//        new HttpRequestTask().doInBackground(lastKnownLocation);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                new HttpRequestTask2().execute();
                Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
                new HttpRequestTask().execute(lastKnownLocation);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private static class Traveller {
        String email, lat, lng;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        Traveller(String email, String lat, String lng) {
            this.email = email;
            this.lat = lat;
            this.lng = lng;
        }
    }

    private static class Greeting {

        private String id;
        private String content;

        public String getId() {
            return this.id;
        }

        public String getContent() {
            return this.content;
        }

    }

    private class HttpRequestTask2 extends AsyncTask<Void, Void, Greeting> {
        @Override
        protected Greeting doInBackground(Void... params) {
            try {
                final String url = "http://rest-service.guides.spring.io/greeting";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Greeting greeting = restTemplate.getForObject(url, Greeting.class);
                return greeting;
            } catch (Exception e) {
                System.out.println("MainActivity :" + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Greeting greeting) {
            System.out.println(greeting);
//            TextView greetingIdText = (TextView) findViewById(R.id.id_value);
//            TextView greetingContentText = (TextView) findViewById(R.id.content_value);
//            greetingIdText.setText(greeting.getId());
//            greetingContentText.setText(greeting.getContent());
        }

    }
    private static final String IP_ADDRESS = "73.15.199.242";

    private void ping(double lat, double lng) {
        final String uri = String.format("http://%d:8080/api/traveller/location", IP_ADDRESS);

//            Map<String, String> params = new HashMap<String, String>();
//        params.put("id", "2");

        Traveller updatedTraveller = new Traveller("Ahsen Jaffer <ahsen.jaffer@gmail.com>", lat+"", lng+"");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        try {
//                restTemplate.put(uri, updatedTraveller, params);
            restTemplate.put(uri, updatedTraveller);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

//        HttpAuthentication httpAuthentication = new HttpBasicAuthentication("admin", "admin");
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.setAuthorization(httpAuthentication);
//
//        HttpEntity<?> httpEntity = new HttpEntity<Object>(params, requestHeaders);
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
//
//        try {
//            restTemplate.exchange(HOST + "/api/traveller/location", HttpMethod.PUT, httpEntity, Traveller.class);
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }

    }

    private class HttpRequestTask extends AsyncTask<Location, Void, Void> {
        private void setupLocationListener() {
            // Acquire a reference to the system Location Manager
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            // Define a listener that responds to location updates
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    // Called when a new location is found by the network location provider.
                    ping(location.getLatitude(), location.getLongitude());
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {}

                public void onProviderEnabled(String provider) {}

                public void onProviderDisabled(String provider) {}
            };

            // Register the listener with the Location Manager to receive location updates
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }

        @Override
        protected Void doInBackground(Location... params) {
            try {
                Location location = params[0];
                ping(location.getLatitude(), location.getLongitude());
//                setupLocationListener();
            } catch (Exception e) {
                System.err.print(e.getMessage());
            }

            return null;
        }


//        @Override
//        protected void onPostExecute(Greeting greeting) {
//            TextView greetingIdText = (TextView) findViewById(R.id.id_value);
//            TextView greetingContentText = (TextView) findViewById(R.id.content_value);
//            greetingIdText.setText(greeting.getId());
//            greetingContentText.setText(greeting.getContent());
//        }

    }



}
