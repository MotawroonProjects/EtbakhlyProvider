package com.etbakhly_provider.uis.activity_setting;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.ActivitySettingsBinding;
import com.etbakhly_provider.model.UserModel;
import com.etbakhly_provider.preferences.Preferences;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_contact_us.ContactUsActivity;
import com.etbakhly_provider.uis.activity_kitchen.KitchenDetailsActivity;
import com.etbakhly_provider.uis.activity_register.RegisterActivity;
import com.etbakhly_provider.uis.activity_setting.setting_details.SettingDetailsActivity;
import com.etbakhly_provider.uis.activity_setting.wallet_activity.WalletActivity;

public class SettingsActivity extends BaseActivity {
    private ActivitySettingsBinding binding;
    private ActivityResultLauncher<Intent> launcher;
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        initView();
    }

    private void initView() {
        preferences = Preferences.getInstance();
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                UserModel userModel = (UserModel) result.getData().getSerializableExtra("data");
                preferences.createUpdateUserData(this, userModel);
                binding.setModel(getUserModel());
            }
        });
        setUpToolbar(binding.toolbarLayout, getString(R.string.settings), R.color.colorPrimary, R.color.white);
        binding.setLang(getLang());
        binding.setModel(getUserModel());
        binding.llKitchenDetails.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, KitchenDetailsActivity.class);
            startActivity(intent);
            finish();
        });
        binding.llSettings.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, SettingDetailsActivity.class);
            startActivity(intent);
        });
        binding.llUpdateProfile.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, RegisterActivity.class);
            launcher.launch(intent);
        });
        binding.llContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, ContactUsActivity.class);
                startActivity(intent);
            }
        });

    }
}