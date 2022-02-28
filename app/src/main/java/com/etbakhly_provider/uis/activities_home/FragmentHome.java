package com.etbakhly_provider.uis.activities_home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.CompletedOrdersAdapter;
import com.etbakhly_provider.adapter.HomePagerAdapter;
import com.etbakhly_provider.databinding.FragmentHomeBinding;
import com.etbakhly_provider.databinding.FragmentOrderBinding;
import com.etbakhly_provider.mvvm.ActivityHomeGeneralMvvm;
import com.etbakhly_provider.mvvm.FragmentCompletedOrdersMvvm;
import com.etbakhly_provider.uis.activities_home.fragments.FragmentCompletedOrders;
import com.etbakhly_provider.uis.activities_home.fragments.FragmentNewOrders;
import com.etbakhly_provider.uis.activities_home.fragments.FragmentPendingOrders;
import com.etbakhly_provider.uis.activity_base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class FragmentHome extends BaseFragment {
    private FragmentHomeBinding binding;
    private HomeActivity activity;
    private HomePagerAdapter pagerAdapter;
    private List<String> titles;
    private List<Fragment> fragmentList;

    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;

    public static FragmentCompletedOrders newInstance() {
        FragmentCompletedOrders fragment = new FragmentCompletedOrders();

        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        activityHomeGeneralMvvm = ViewModelProviders.of(activity).get(ActivityHomeGeneralMvvm.class);

        titles = new ArrayList<>();
        fragmentList = new ArrayList<>();

        titles.add(getString(R.string.new_orders));
        titles.add(getString(R.string.pending));
        titles.add(getString(R.string.completed));

        fragmentList.add(FragmentNewOrders.newInstance());
        fragmentList.add(FragmentPendingOrders.newInstance());
        fragmentList.add(FragmentCompletedOrders.newInstance());

        binding.pager.setOffscreenPageLimit(fragmentList.size());

        pagerAdapter = new HomePagerAdapter(getChildFragmentManager(), PagerAdapter.POSITION_UNCHANGED, fragmentList, titles);
        binding.tab.setupWithViewPager(binding.pager);
        binding.pager.setAdapter(pagerAdapter);

        activityHomeGeneralMvvm.getPosChangedSuccess().observe(activity, this::setItemPos);

    }

    public void setItemPos(int pos) {
        binding.pager.setCurrentItem(pos);
    }
}
