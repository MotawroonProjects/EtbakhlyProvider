package com.etbakhly_provider.uis.activity_setting.setting_details;

import androidx.activity.result.ActivityResultLauncher;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.ActivitySettingDetailsBinding;
import com.etbakhly_provider.model.UserModel;
import com.etbakhly_provider.model.UserSettingsModel;
import com.etbakhly_provider.mvvm.ActivitySettingDetailsMvvm;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_web_view.WebViewActivity;

public class SettingDetailsActivity extends BaseActivity {
    private ActivitySettingDetailsBinding binding;
    private ActivitySettingDetailsMvvm mvvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting_details);
        initView();
    }

    private void initView() {
        mvvm = ViewModelProviders.of(this).get(ActivitySettingDetailsMvvm.class);
        binding.setLang(getLang());
        binding.setSettings(getUserSettings());
        binding.setStatus(getUserModel().getData().getCaterer().getStatus().equals("busy"));
        binding.llBack.setOnClickListener(view -> finish());


        binding.switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            UserSettingsModel userSettingsModel = getUserSettings();
            userSettingsModel.setCanSendNotifications(isChecked);
            setUserSettings(userSettingsModel);
        });
        binding.llTerms.setOnClickListener(v -> {
            navigateToWebViewActivity("http://etbo5ly.coopq8.com/terms#1");
        });

        binding.llPrivacy.setOnClickListener(v -> {
            navigateToWebViewActivity("http://etbo5ly.coopq8.com/terms#2");
        });

        mvvm.getOnStatusUpdated().observe(this, status -> {
            UserModel userModel = getUserModel();

            userModel.getData().getCaterer().setStatus(status ? "busy" : "online");
            setUserModel(userModel);
            binding.setStatus(status);
        });

        binding.switchBusy.setOnClickListener(v -> {
            boolean isChecked = binding.switchBusy.isChecked();
            mvvm.updateStatus(isChecked, getUserModel().getData().getCaterer().getId());

        });


    }

    private void navigateToWebViewActivity(String url) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}