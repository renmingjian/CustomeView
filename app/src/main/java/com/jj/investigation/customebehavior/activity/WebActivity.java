package com.jj.investigation.customebehavior.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jj.investigation.customebehavior.R;
import com.jj.investigation.customebehavior.view.VerticalScrollView;

/**
 * Created by ${R.js} on 2018/3/21.
 */

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private VerticalScrollView verticalScrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = (WebView) findViewById(R.id.webView);
        verticalScrollView = (VerticalScrollView) findViewById(R.id.verticalScrollView);
        verticalScrollView.close();
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://www.baidu.com");
    }
}
