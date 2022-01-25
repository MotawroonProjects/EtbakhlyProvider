package com.etbakhly_provider.uis.activity_login;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.ActivityLoginBinding;
import com.etbakhly_provider.model.LoginModel;
import com.etbakhly_provider.mvvm.ActivityLoginMvvm;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.uis.activity_base.BaseActivity;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    private LoginModel model;
    private ActivityLoginMvvm activityLoginMvvm;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initView();
    }


    private void initView() {
        activityLoginMvvm = ViewModelProviders.of(this).get(ActivityLoginMvvm.class);
        activityLoginMvvm.onLoginSuccess().observe(this, userModel -> {
            setUserModel(userModel);
            navigateToHomActivity();
        });

        model = new LoginModel();
        binding.setModel(model);
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    navigateToHomActivity();
                }
            }
        });
        binding.btnLogin.setOnClickListener(v -> {
            if (model.isDataValid(this)) {
                Common.CloseKeyBoard(this, binding.edtPass);
                activityLoginMvvm.login(this, model);
            }
        });


    }

    private void navigateToHomActivity() {

    }


}