package com.etbakhly_provider.uis.activity_kitchen;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.KitchenPagerAdapter;
import com.etbakhly_provider.databinding.ActivityKitchenDetailsBinding;

import com.etbakhly_provider.model.KitchenModel;
import com.etbakhly_provider.model.NotResponse;
import com.etbakhly_provider.model.ZoneCover;
import com.etbakhly_provider.mvvm.ActivityHomeGeneralMvvm;
import com.etbakhly_provider.mvvm.ActivityKitchenDetailsMvvm;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_kitchen.fragments_kitchen.FragmentDeliveryAreas;
import com.etbakhly_provider.uis.activity_kitchen.fragments_kitchen.FragmentGallery;
import com.etbakhly_provider.uis.activity_kitchen.fragments_kitchen.FragmentRatings;
import com.etbakhly_provider.uis.activity_kitchen.fragments_kitchen.FragmentServiceTruck;
import com.etbakhly_provider.uis.activity_kitchen.fragments_kitchen.FragmentServices;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class KitchenDetailsActivity extends BaseActivity {
    ActivityKitchenDetailsBinding binding;
    private List<String> titles;
    private List<Fragment> fragmentList;
    private KitchenPagerAdapter pagerAdapter;
    private ActivityKitchenDetailsMvvm mvvm;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;

    private KitchenModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_kitchen_details);
        initView();
    }

    private void initView() {

        setSupportActionBar(binding.toolbar);
        mvvm = ViewModelProviders.of(this).get(ActivityKitchenDetailsMvvm.class);

        titles = new ArrayList<>();
        fragmentList = new ArrayList<>();

        binding.setLang(getLang());

        mvvm.getIsDataLoading().observe(this, isLoading -> {
            if (isLoading) {
                binding.coordinator.setVisibility(View.GONE);
                binding.loader.setVisibility(View.VISIBLE);
                binding.loader.startShimmer();
            } else {
                binding.coordinator.setVisibility(View.VISIBLE);
                binding.loader.setVisibility(View.GONE);
                binding.loader.stopShimmer();
            }
        });

        mvvm.onDataSuccess().observe(this, kitchenModel -> {
            model = kitchenModel;
            updateUi();
        });
//        String user_id = null;
//        if (getUserModel() != null) {
//            user_id = getUserModel().getData().getId();
//        }

        mvvm.getKitchenData(getUserModel().getData().getCaterer().getId(), "13");

        binding.llBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void updateUi() {
        activityHomeGeneralMvvm = ViewModelProviders.of(this).get(ActivityHomeGeneralMvvm.class);

        binding.setModel(model);

        titles.add(getString(R.string.services));
        titles.add(getString(R.string.gallery));
        titles.add(getString(R.string.ratings));
        titles.add(getString(R.string.delivery_areas));

        if (getUserModel().getData().getType().equals("food_track")) {
            fragmentList.add(FragmentServiceTruck.newInstance(model));
        } else {
            fragmentList.add(FragmentServices.newInstance(model));

        }

        fragmentList.add(FragmentGallery.newInstance(model));
        fragmentList.add(FragmentRatings.newInstance(model));
        fragmentList.add(FragmentDeliveryAreas.newInstance(model));

        pagerAdapter = new KitchenPagerAdapter(getSupportFragmentManager(), PagerAdapter.POSITION_UNCHANGED, fragmentList, titles);
        binding.tab.setupWithViewPager(binding.pager);
        binding.pager.setAdapter(pagerAdapter);
        binding.pager.setOffscreenPageLimit(fragmentList.size());

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public void updateBlur(float blur) {
        if (blur > 0) {
            binding.blur.setVisibility(View.VISIBLE);
        } else {
            binding.blur.setVisibility(View.GONE);

        }
        binding.blur.setBlurRadius(blur);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGalleryUploaded(NotResponse response) {
        activityHomeGeneralMvvm.onGallerySuccess().setValue(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}