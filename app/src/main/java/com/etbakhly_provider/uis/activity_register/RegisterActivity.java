package com.etbakhly_provider.uis.activity_register;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.SpinnerServiceAdapter;
import com.etbakhly_provider.databinding.ActivityRegisterBinding;
import com.etbakhly_provider.model.RegisterModel;
import com.etbakhly_provider.model.UserModel;
import com.etbakhly_provider.mvvm.ActivitySignupMvvm;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends BaseActivity {
    private ActivityRegisterBinding binding;
    private RegisterModel model;
    private ActivitySignupMvvm activitySignupMvvm;
    private String phone_code, phone;
    private ActivityResultLauncher<Intent> launcher;
    private UserModel userModel;
    private SpinnerServiceAdapter spinnerServiceAdapter;
    private List<String> servicelist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        phone_code = intent.getStringExtra("phone_code");
        phone = intent.getStringExtra("phone");

    }

    private void initView() {
        activitySignupMvvm = ViewModelProviders.of(this).get(ActivitySignupMvvm.class);
        model = new RegisterModel(phone_code, phone);
        servicelist = new ArrayList<>();
        spinnerServiceAdapter = new SpinnerServiceAdapter(this);
        binding.spinnerServices.setAdapter(spinnerServiceAdapter);
        binding.spinnerServices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    if (i == 1) {
                        model.setService("service");
                    } else if (i == 2) {
                        model.setService("chef");
                    } else if (i == 3) {
                        model.setService("food_track");

                    }
                } else {
                    model.setService("");
                }
                binding.setModel(model);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        setdata();
        userModel = getUserModel();
        if (userModel != null) {
            model.setPhone_code(userModel.getData().getPhone_code());
            model.setPhone(userModel.getData().getPhone());


        }
        binding.setModel(model);

        activitySignupMvvm.getUserData().observe(this, userModel -> {
            Intent intent = getIntent();
            intent.putExtra("data", userModel);
            setResult(RESULT_OK, intent);
            finish();

        });


        binding.btnNext.setOnClickListener(view -> {
            if (model.isDataValid(this)) {

                if (userModel == null) {
                    activitySignupMvvm.signUp(model, this);
                } else {
                    //  activitySignupMvvm.update(model, userModel.getData().getId(), this);
                }

            }
        });

    }

    private void setdata() {
        servicelist.add(getResources().getString(R.string.ch_service));
        servicelist.add(getResources().getString(R.string.service));
        servicelist.add(getResources().getString(R.string.chef));
        servicelist.add(getResources().getString(R.string.food_truck));
        spinnerServiceAdapter.updateData(servicelist);

    }


}