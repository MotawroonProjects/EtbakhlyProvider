package com.etbakhly_provider.uis.activity_web_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.ActivityWebViewBinding;
import com.etbakhly_provider.uis.activity_base.BaseActivity;

public class WebViewActivity extends BaseActivity {
    private ActivityWebViewBinding binding;
    private String url ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
    }

    private void initView() {
        setUpToolbar(binding.toolbar, getString(R.string.back), R.color.colorPrimary, R.color.white);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setSupportZoom(false);
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                binding.progBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                binding.progBar.setVisibility(View.VISIBLE);
            }
        });

        binding.webView.loadUrl(url);

    }
}