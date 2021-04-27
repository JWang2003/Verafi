package com.example.misinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class WebPage extends AppCompatActivity {

    String weblink;
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        Intent intent = getIntent();
        weblink = intent.getStringExtra("url");
        web = findViewById(R.id.webview);
        web.loadUrl(weblink);
    }
}