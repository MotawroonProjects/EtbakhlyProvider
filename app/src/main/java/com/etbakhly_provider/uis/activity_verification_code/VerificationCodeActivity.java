package com.etbakhly_provider.uis.activity_verification_code;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.ActivityVerificationCodeBinding;
import com.etbakhly_provider.model.UserModel;
import com.etbakhly_provider.model.UserSettingsModel;
import com.etbakhly_provider.mvvm.ActivityVerificationMvvm;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_register.RegisterActivity;
import com.etbakhly_provider.uis.activity_signup.SignupActivity;

public class VerificationCodeActivity extends BaseActivity {

    private ActivityVerificationCodeBinding binding;
    private String phone_code = "";
    private String phone = "";
    private ActivityVerificationMvvm mvvm;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verification_code);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        phone_code = intent.getStringExtra("phone_code");
        phone = intent.getStringExtra("phone");
    }

    private void initView() {
        mvvm = ViewModelProviders.of(this).get(ActivityVerificationMvvm.class);
        mvvm.getSmsCode().observe(this, code -> {
            binding.edtCode.setText(code);
        });
        mvvm.getTime().observe(this, time -> {
            binding.tvCounter.setText(time);
        });

        mvvm.canResend().observe(this, canSend -> {
            binding.tvResend.setEnabled(canSend);

            if (canSend) {
                binding.tvResend.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));


            } else {
                binding.tvResend.setTextColor(ContextCompat.getColor(this, R.color.gray11));

            }

        });

        mvvm.getUserData().observe(this, userModel -> {
            if (userModel == null) {
                navigateToSignUpActivity();
            } else {
                Intent intent=getIntent();
                intent.putExtra("data",userModel);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        mvvm.sendSmsCode(getLang(), phone_code, phone, this);

        binding.tvResend.setEnabled(false);
        binding.tvResend.setOnClickListener(view -> mvvm.sendSmsCode(getLang(), phone_code, phone, this));
        binding.setPhone(phone_code + phone);
        binding.btnConfirm.setOnClickListener(view -> {
            String smsCode = binding.edtCode.getText().toString();
            if (!smsCode.isEmpty()) {
                mvvm.checkValidCode(smsCode, phone_code, phone, this);
            } else {
                binding.edtCode.setError(getResources().getString(R.string.field_required));
            }
        });

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                UserModel userModel= (UserModel) result.getData().getSerializableExtra("data");
                Intent intent=getIntent();
                intent.putExtra("data",userModel);
                setResult(RESULT_OK,intent);
                finish();
            }
        });


    }

    private void navigateToSignUpActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("phone_code", phone_code);
        intent.putExtra("phone", phone);
        launcher.launch(intent);
    }
}