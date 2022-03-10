package com.etbakhly_provider.uis.activity_setting.setting_details;

import androidx.activity.result.ActivityResultLauncher;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.ActivitySettingDetailsBinding;
import com.etbakhly_provider.model.UserSettingsModel;
import com.etbakhly_provider.uis.activity_base.BaseActivity;

public class SettingDetailsActivity extends BaseActivity {
    private ActivitySettingDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting_details);
        initView();
    }

    private void initView() {
        binding.setLang(getLang());
        binding.setSettings(getUserSettings());
        binding.llBack.setOnClickListener(view -> finish());

        binding.switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            UserSettingsModel userSettingsModel = getUserSettings();
            userSettingsModel.setCanSendNotifications(isChecked);
            setUserSettings(userSettingsModel);
        });
    }
}