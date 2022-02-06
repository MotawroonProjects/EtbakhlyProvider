package com.etbakhly_provider.uis.activity_login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.ActivityLoginBinding;
import com.etbakhly_provider.databinding.BottomSheetDialogBinding;
import com.etbakhly_provider.model.LoginModel;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.uis.activities_fragments_home.HomeActivity;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_verification_code.VerificationCodeActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    private LoginModel model;
    private boolean isAccepted = false;
    private int req;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initView();
    }

    private void initView() {
        model = new LoginModel();
        binding.setModel(model);

        binding.edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith("0")) {
                    binding.edtPhone.setText("");
                }
            }
        });

        binding.btnLogin.setOnClickListener(v -> {
            if (model.isDataValid(this)) {
                Common.CloseKeyBoard(this, binding.edtPhone);
//                openSheet();
                navigateToVerificationCodeActivity();
            }
        });



        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == RESULT_OK) {
//                navigateToAppCategoryActivity("login");
            } else if (req == 2 && result.getResultCode() == RESULT_OK && result.getData() != null) {
                String option_id = result.getData().getStringExtra("option_id");
                navigateToHomeActivity(option_id);
            }
        });
    }

    private void navigateToHomeActivity(String option_id) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("option_id", option_id);
        startActivity(intent);
        if (getUserModel() != null) {
            finish();
        }

    }


//    private void navigateToAppCategoryActivity(String action) {
//        req = 2;
//        Intent intent = new Intent(this, AppCategoryActivity.class);
//        intent.putExtra("action", action);
//        launcher.launch(intent);
//
//
//    }

    private void openSheet() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        BottomSheetDialogBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.bottom_sheet_dialog, null, false);
        dialog.setContentView(dialogBinding.getRoot());
        dialog.setCanceledOnTouchOutside(false);
        dialogBinding.tv.setText(Html.fromHtml(getString(R.string.do_read_our_terms_of_use_and_privacy_policy)));
        dialogBinding.tv.setMovementMethod(LinkMovementMethod.getInstance());
        dialogBinding.tv.setClickable(true);
        dialogBinding.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> isAccepted = isChecked);
        dialogBinding.btnConfirm.setOnClickListener(v -> {
            if (isAccepted) {
                navigateToVerificationCodeActivity();
            } else {
                Common.showSnackBar(binding.root, getString(R.string.cnt_login));
            }

            dialog.dismiss();

        });
        dialog.show();


    }

    private void navigateToVerificationCodeActivity() {
        req = 1;
        Intent intent = new Intent(this, VerificationCodeActivity.class);
        intent.putExtra("phone_code", model.getPhone_code());
        intent.putExtra("phone", model.getPhone());
        launcher.launch(intent);
    }

}