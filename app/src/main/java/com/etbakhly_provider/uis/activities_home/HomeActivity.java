package com.etbakhly_provider.uis.activities_home;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Intent;
import android.os.Bundle;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.HomePagerAdapter;
import com.etbakhly_provider.databinding.ActivityHomeBinding;
import com.etbakhly_provider.mvvm.ActivityHomeGeneralMvvm;
import com.etbakhly_provider.uis.activities_home.fragments.FragmentNewOrders;
import com.etbakhly_provider.uis.activities_home.fragments.FragmentCompletedOrders;
import com.etbakhly_provider.uis.activities_home.fragments.FragmentPendingOrders;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_setting.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class HomeActivity extends BaseActivity {
    private ActivityHomeBinding binding;
    private String lang;
    private HomePagerAdapter pagerAdapter;
    private List<String> titles;
    private List<Fragment> fragmentList;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initView();

    }

    public void setItemPos(int pos) {
        binding.pager.setCurrentItem(pos);
    }

    private void initView() {
        activityHomeGeneralMvvm = ViewModelProviders.of(this).get(ActivityHomeGeneralMvvm.class);


        titles = new ArrayList<>();
        fragmentList = new ArrayList<>();
        Paper.init(this);
        binding.setLang(lang);

        titles.add(getString(R.string.new_orders));
        titles.add(getString(R.string.pending));
        titles.add(getString(R.string.completed));

        fragmentList.add(FragmentNewOrders.newInstance());
        fragmentList.add(FragmentPendingOrders.newInstance());
        fragmentList.add(FragmentCompletedOrders.newInstance());

        pagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), PagerAdapter.POSITION_UNCHANGED, fragmentList, titles);
        binding.tab.setupWithViewPager(binding.pager);
        binding.pager.setAdapter(pagerAdapter);
//activityHomeGeneralMvvm.getOnStatusSuccess().observe(this, new Observer<String>() {
//    @Override
//    public void onChanged(String s) {
//        if(s.equals("approval")){
//            setItemPos(1);
//        }
//    }
//});

        binding.llMenu.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);

        });
    }
}