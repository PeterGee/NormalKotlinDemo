package com.example.myapplication.testAcivity;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.myapplication.router.ARouterConstants;
import com.example.myapplication.R;

@Route(path = ARouterConstants.COM_URL)
public class TestWebview extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_webview);
        this.webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/schame-test.html");
    }
}
