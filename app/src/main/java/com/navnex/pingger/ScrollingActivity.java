package com.navnex.pingger;

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
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                new HttpRequestTask().execute();
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

    private static final String HOST = "http://192.168.1.6:8080";

    private static class Traveller {
        String email;
        String lat;
        String lng;

        Traveller(String email, String lat, String lng) {
            this.email = email;
            this.lat = lat;
            this.lng = lng;
        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                ping(123,343);
            } catch (Exception e) {
                System.err.print(e.getMessage());
            }

            return null;
        }

        private void ping(double lat, double lng) {
            final String uri = HOST + "/api/traveller/location";

            Map<String, String> params = new HashMap<String, String>();
//        params.put("id", "2");

            Traveller updatedTraveller = new Traveller("ahsen.jaffer@gmail.com", lat+"", lng+"");

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
            try {
                restTemplate.put(uri, updatedTraveller, params);
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

//        @Override
//        protected void onPostExecute(Greeting greeting) {
//            TextView greetingIdText = (TextView) findViewById(R.id.id_value);
//            TextView greetingContentText = (TextView) findViewById(R.id.content_value);
//            greetingIdText.setText(greeting.getId());
//            greetingContentText.setText(greeting.getContent());
//        }

    }



}
