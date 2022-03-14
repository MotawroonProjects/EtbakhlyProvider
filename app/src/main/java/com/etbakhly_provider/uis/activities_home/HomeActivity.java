package com.etbakhly_provider.uis.activities_home;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.HomePagerAdapter;
import com.etbakhly_provider.databinding.ActivityHomeBinding;
import com.etbakhly_provider.databinding.DrawerHeaderBinding;
import com.etbakhly_provider.language.Language;
import com.etbakhly_provider.model.NotResponse;
import com.etbakhly_provider.model.NotiFire;
import com.etbakhly_provider.model.UserSettingsModel;
import com.etbakhly_provider.mvvm.ActivityHomeGeneralMvvm;
import com.etbakhly_provider.uis.activities_home.fragments.FragmentNewOrders;
import com.etbakhly_provider.uis.activities_home.fragments.FragmentCompletedOrders;
import com.etbakhly_provider.uis.activities_home.fragments.FragmentPendingOrders;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_login.LoginActivity;
import com.etbakhly_provider.uis.activity_notifications.NotificationsActivity;
import com.etbakhly_provider.uis.activity_setting.SettingsActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class HomeActivity extends BaseActivity {
    private ActivityHomeBinding binding;
    private NavController navController;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;
    private ActivityResultLauncher<Intent> launcher;
    private int req;
    private String order_id = "";// is from firebase notification
    private boolean isFromFireBase = false;
    private  DrawerHeaderBinding drawerHeaderBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("firebase")) {
            isFromFireBase = true;
            order_id = intent.getStringExtra("firebase");
        }

    }


    private void initView() {
        activityHomeGeneralMvvm = ViewModelProviders.of(this).get(ActivityHomeGeneralMvvm.class);
        activityHomeGeneralMvvm.onLoggedOutSuccess().observe(this, isLoggedOut -> {
            if (isLoggedOut) {

                clearUserData();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        activityHomeGeneralMvvm.onTokenSuccess().observe(this, this::setUserModel);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getUserModel().getData().getType().equals("food_track")) {
            binding.navView.inflateMenu(R.menu.menu_truck);

        } else {
            binding.navView.inflateMenu(R.menu.menu);
        }


        binding.setModel(getUserModel());
        binding.setLang(getLang());
        drawerHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.drawer_header, null, false);
        drawerHeaderBinding.setModel(getUserModel());
        binding.navView.addHeaderView(drawerHeaderBinding.getRoot());

        navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(binding.navView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerView);
        binding.llLogout.setOnClickListener(view -> activityHomeGeneralMvvm.logout(getUserModel(), HomeActivity.this));
        activityHomeGeneralMvvm.updateToken(getUserModel());

        if (isFromFireBase) {
            Intent intent = new Intent(this, NotificationsActivity.class);
            startActivity(intent);
        }


        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == RESULT_OK && result.getData() != null) {
                String lang = result.getData().getStringExtra("lang");
                refreshActivity(lang);
            }
        });

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getUserModel()!=null){
            binding.setModel(getUserModel());
            drawerHeaderBinding.setModel(getUserModel());
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, binding.drawerView);
    }

    public void refreshActivity(String lang) {
        Paper.book().write("lang", lang);
        Language.setNewLocale(this, lang);
        new Handler()
                .postDelayed(() -> {

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }, 500);


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewDataRefresh(NotiFire notiFire) {
        activityHomeGeneralMvvm.getOnFragmentNewOrderRefreshed().setValue(true);
    }


    @Override
    public void onBackPressed() {
        if (binding.drawerView.isDrawerOpen(GravityCompat.START)) {
            binding.drawerView.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}