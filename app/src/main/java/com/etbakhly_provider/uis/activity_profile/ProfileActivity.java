package com.etbakhly_provider.uis.activity_profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.ProfilePagerAdapter;
import com.etbakhly_provider.databinding.ActivityProfileBinding;

import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_profile.fragments_profile.FragmentDeliveryAreas;
import com.etbakhly_provider.uis.activity_profile.fragments_profile.FragmentGallery;
import com.etbakhly_provider.uis.activity_profile.fragments_profile.FragmentRatings;
import com.etbakhly_provider.uis.activity_profile.fragments_profile.FragmentServices;
import com.etbakhly_provider.uis.activity_setting.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class ProfileActivity extends BaseActivity {
    ActivityProfileBinding binding;
    private List<String> titles;
    private List<Fragment> fragmentList;
    private ProfilePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        initView();
    }

    private void initView() {
        titles = new ArrayList<>();
        fragmentList = new ArrayList<>();
        Paper.init(this);
        binding.setLang(getLang());

        titles.add(getString(R.string.services));
        titles.add(getString(R.string.gallery));
        titles.add(getString(R.string.ratings));
        titles.add(getString(R.string.delivery_areas));

        fragmentList.add(FragmentServices.newInstance());
        fragmentList.add(FragmentGallery.newInstance());
        fragmentList.add(FragmentRatings.newInstance());
        fragmentList.add(FragmentDeliveryAreas.newInstance());

        pagerAdapter = new ProfilePagerAdapter(getSupportFragmentManager(), PagerAdapter.POSITION_UNCHANGED, fragmentList, titles);
        binding.tab.setupWithViewPager(binding.pager);
        binding.pager.setAdapter(pagerAdapter);

        binding.btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
            startActivity(intent);
            finish();
        });
    }
}