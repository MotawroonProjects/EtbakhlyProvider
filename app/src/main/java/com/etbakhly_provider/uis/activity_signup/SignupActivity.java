package com.etbakhly_provider.uis.activity_signup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.ActivitySignUpBinding;
import com.etbakhly_provider.language.Language;
import com.etbakhly_provider.model.UserModel;
import com.etbakhly_provider.preferences.Preferences;
import com.etbakhly_provider.uis.activity_base.BaseActivity;

import io.paperdb.Paper;

public class SignupActivity extends BaseActivity  {
    private ActivitySignUpBinding binding;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        initView();


    }

    private void initView() {
        navController = Navigation.findNavController(this, R.id.navHostFragment);
      //  NavigationUI.setupActionBarWithNavController(this, navController);




    }




    @Override
    public void onBackPressed() {
        int currentFragmentId = navController.getCurrentDestination().getId();
        if (currentFragmentId == R.id.signup1) {
            finish();

        }  else {
            navController.popBackStack();
        }

    }





}
