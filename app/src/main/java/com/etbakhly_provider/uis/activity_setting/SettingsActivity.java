package com.etbakhly_provider.uis.activity_setting;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.ActivitySettingsBinding;
import com.etbakhly_provider.uis.activity_base.BaseActivity;

public class SettingsActivity extends BaseActivity {
    private ActivitySettingsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_settings);
        initView();
    }

    private void initView() {
        binding.setLang(getLang());

    }
}