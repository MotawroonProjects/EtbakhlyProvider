package com.etbakhly_provider.uis.activities_fragments_home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.HomePagerAdapter;
import com.etbakhly_provider.databinding.ActivityHomeBinding;
import com.etbakhly_provider.uis.activities_fragments_home.fragments.FragmentAwaitingApproval;
import com.etbakhly_provider.uis.activities_fragments_home.fragments.FragmentCompleted;
import com.etbakhly_provider.uis.activities_fragments_home.fragments.FragmentUnderway;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_home);
        initView();

    }

    private void initView() {
        titles = new ArrayList<>();
        fragmentList = new ArrayList<>();
        Paper.init(this);
        binding.setLang(lang);

        titles.add(getString(R.string.awaiting_approval));
        titles.add(getString(R.string.underway));
        titles.add(getString(R.string.completed));

        fragmentList.add(FragmentAwaitingApproval.newInstance());
        fragmentList.add(FragmentUnderway.newInstance());
        fragmentList.add(FragmentCompleted.newInstance());

        pagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), PagerAdapter.POSITION_UNCHANGED, fragmentList,titles);
        binding.tab.setupWithViewPager(binding.pager);
        binding.pager.setAdapter(pagerAdapter);

        FragmentAwaitingApproval fragmentAwaitingApproval=(FragmentAwaitingApproval)fragmentList.get(0);

        binding.llMenu.setOnClickListener(view -> {
            Intent intent=new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);

        });
    }
}