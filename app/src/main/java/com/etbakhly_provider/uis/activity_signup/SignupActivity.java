package com.etbakhly_provider.uis.activity_signup;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.MyPagerAdapter;
import com.etbakhly_provider.databinding.ActivitySignUpBinding;
import com.etbakhly_provider.model.AddZoneModel;
import com.etbakhly_provider.model.DeliveryModel;
import com.etbakhly_provider.model.SignUpModel;
import com.etbakhly_provider.model.StoreCatreerDataModel;
import com.etbakhly_provider.model.UserModel;
import com.etbakhly_provider.mvvm.ActivitySignupMvvm;
import com.etbakhly_provider.mvvm.GeneralSignUpMvvm;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_signup.fragments_sign_up_navigation.FragmentSignup1;
import com.etbakhly_provider.uis.activity_signup.fragments_sign_up_navigation.FragmentSignup2;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends BaseActivity {
    private ActivitySignUpBinding binding;
    private UserModel userModel;
    private GeneralSignUpMvvm generalSignUpMvvm;
    private ActivitySignupMvvm mvvm;
    private List<Fragment> fragments;
    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        getDataFromIntent();
        initView();


    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        userModel = (UserModel) intent.getSerializableExtra("data");

    }

    private void initView() {

        fragments = new ArrayList<>();
        generalSignUpMvvm = ViewModelProviders.of(this).get(GeneralSignUpMvvm.class);

        mvvm = ViewModelProviders.of(this).get(ActivitySignupMvvm.class);
        mvvm.getUserData().observe(this, userModel -> {
            Intent intent = getIntent();
            intent.putExtra("data", userModel);
            setResult(RESULT_OK, intent);
            finish();
        });
        fragments.add(FragmentSignup1.newInstance());
        fragments.add(FragmentSignup2.newInstance());

        adapter = new MyPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragments, new ArrayList<>());
        binding.pager.setAdapter(adapter);
        binding.pager.setOffscreenPageLimit(fragments.size());

    }

    public void nextStep(SignUpModel model) {
        generalSignUpMvvm.getSignUpModel().setValue(model);
        binding.pager.setCurrentItem(1);
    }

    @Override
    public void onBackPressed() {
        if (binding.pager.getCurrentItem() == 0) {
            finish();
        } else {
            binding.pager.setCurrentItem(0);
        }

    }


}
