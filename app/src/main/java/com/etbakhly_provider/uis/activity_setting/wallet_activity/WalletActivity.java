package com.etbakhly_provider.uis.activity_setting.wallet_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.TransactionAdapter;
import com.etbakhly_provider.databinding.ActivityWalletBinding;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
public class WalletActivity extends BaseActivity {
    private ActivityWalletBinding binding;
    private TransactionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet);
        initView();

    }

    private void initView() {
        setSupportActionBar(binding.toolbar);
        binding.setLang(getLang());

        adapter=new TransactionAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        binding.llBack.setOnClickListener(view -> finish());

        binding.btnWithDraw.setOnClickListener(view -> {

        });

    }
}