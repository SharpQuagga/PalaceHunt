package com.mycompany.secondproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MapsURLActivity extends AppCompatActivity {

    WebView webView;
    WebViewClient client;


    void initViews(){
        webView = findViewById(R.id.webView);

        client = new WebViewClient();

        webView.setWebViewClient(client);
        webView.getSettings().setJavaScriptEnabled(true); // Turn On Javascript
        Log.e("City","City");
        Intent rcv = getIntent();
        String city = rcv.getStringExtra("City");
        Log.e("City","City"+city);

        if (city.contains("Ludhiana"))
        webView.loadUrl("https://www.google.com/maps/search/palace+in+ludhiana/@30.8988519,75.8521855,13z/data=!3m1!4b1");

        if (city.contains("Chandigarh"))
            webView.loadUrl("https://www.google.com/maps/search/palace+in+chandigarh/@30.7369336,76.7566645,13z/data=!3m1!4b1");

        if (city.contains("Sangrur"))
            webView.loadUrl("https://www.google.com/maps/search/palace+in+sangrur/@30.2596117,75.7845351,12z/data=!3m1!4b1");

        if (city.contains("Delhi"))
            webView.loadUrl("https://www.google.com/maps/search/palace+in+delhi/@28.5806463,77.0636552,11.65z");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_url);
        initViews();
    }
}
