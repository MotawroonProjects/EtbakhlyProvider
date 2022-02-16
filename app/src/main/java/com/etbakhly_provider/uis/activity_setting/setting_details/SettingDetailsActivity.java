package com.etbakhly_provider.uis.activity_setting.setting_details;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.DeliveryZoneAdapter;
import com.etbakhly_provider.databinding.ActivitySettingDetailsBinding;
import com.etbakhly_provider.uis.activity_base.BaseActivity;

public class SettingDetailsActivity extends BaseActivity {
    private ActivitySettingDetailsBinding binding;
    private DeliveryZoneAdapter adapter;
    private ActivityResultLauncher<Intent> launcher;
    private int req;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_setting_details);
        initView();
    }

    private void initView() {
        binding.setLang(getLang());

        adapter=new DeliveryZoneAdapter(this);
        binding.recViewDelZones.setLayoutManager(new GridLayoutManager(this,3));
        binding.recViewDelZones.setAdapter(adapter);
        binding.llBack.setOnClickListener(view -> finish());
    }
}