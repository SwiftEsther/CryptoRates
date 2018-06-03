package com.swiftesther.cryptorates;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

public class MainActivity extends AppCompatActivity {


    private static final String REQUESTTAG = "string request first";
    private String answer;
    private RequestQueue mRequestQueue;
    private final String URL = "https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=ETH,EUR,GBP,RUB,AUD,AED,NGN";
    private final String URL2 = "https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=EUR,GBP,RUB,AUD,AED,NGN,CAD,SGD,HKD,PLN,ZAR,PHP,RUR,HUF,BRL,MXN,RON,BGN,TRY,BHD";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Find the view that shows the bitcoin card
        ImageButton bitcoin = (ImageButton) findViewById(R.id.bitcoin);

        //Find the view that shows the etherum card
        ImageButton etherum = (ImageButton) findViewById(R.id.etherum);

        //Set Clicklisteners on the respective views
        bitcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a new intent to open the {@link BitcoinRatesActivity}
                Intent bitcoinRatesIntent = new Intent(MainActivity.this, BitcoinRatesActivity.class);

                //Start the new activity
                startActivity(bitcoinRatesIntent);
            }
        });
        etherum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a new intent to open the {@link EtherumRatesActivity}
                Intent etherumRatesIntent = new Intent(MainActivity.this, EtherumRatesActivity.class);

                //Start the new activity
                startActivity(etherumRatesIntent);
            }
        });
        sendRequest();
    }

    public void sendRequest() {

        //cache for http request
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB capacity
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());
        String api = URL;
        String api2 = URL2;

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);
        // Start the queue
        mRequestQueue.start();


        answer = new String();
        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //save the response to shared preference
                        SharedPreferences result = getSharedPreferences("PREF", 0);
                        SharedPreferences.Editor editor = result.edit();
                        editor.putString("results", response.toString());
                        editor.commit();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(getApplicationContext(), "Please check connection", Toast.LENGTH_SHORT).show();

                    }
                });
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, api2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //save the response to shared preference
                        SharedPreferences result = getSharedPreferences("PREF2", 0);
                        SharedPreferences.Editor editor = result.edit();
                        editor.putString("results", response.toString());
                        editor.commit();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(getApplicationContext(), "Please check connection", Toast.LENGTH_SHORT).show();

                    }
                });


        // Add the request to the RequestQueue.
        stringRequest.setTag(REQUESTTAG);
        //mRequestQueue.stop();
        mRequestQueue.add(stringRequest);
        mRequestQueue.add(stringRequest2);

    }
}