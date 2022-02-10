package com.etbakhly_provider.uis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.ActivityFeastsBinding;
import com.etbakhly_provider.uis.activity_base.BaseActivity;

public class FeastsActivity extends BaseActivity {
    ActivityFeastsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_feasts);
        initView();
    }

    private void initView() {
    }
}