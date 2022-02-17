package com.etbakhly_provider.uis.activity_setting;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.ActivitySettingsBinding;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_kitchen.KitchenDetailsActivity;
import com.etbakhly_provider.uis.activity_setting.setting_details.SettingDetailsActivity;
import com.etbakhly_provider.uis.activity_setting.wallet_activity.WalletActivity;

public class SettingsActivity extends BaseActivity {
    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        initView();
    }

    private void initView() {
        binding.setLang(getLang());
        binding.btnBack.setOnClickListener(view -> finish());
        binding.llKitchenDetails.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, KitchenDetailsActivity.class);
            startActivity(intent);
            finish();
        });
        binding.llSettings.setOnClickListener(view -> {
            Intent intent=new Intent(SettingsActivity.this, SettingDetailsActivity.class);
            startActivity(intent);
        });
        binding.llWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingsActivity.this, WalletActivity.class);
                startActivity(intent);
            }
        });
    }
}