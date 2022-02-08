package com.etbakhly_provider.uis.activities_fragments_home.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.CompletedOrdersAdapter;
import com.etbakhly_provider.databinding.FragmentOrderBinding;
import com.etbakhly_provider.mvvm.ActivityHomeGeneralMvvm;
import com.etbakhly_provider.mvvm.FragmentCompletedOrdersMvvm;
import com.etbakhly_provider.uis.activities_fragments_home.HomeActivity;
import com.etbakhly_provider.uis.activity_base.BaseFragment;


public class FragmentCompletedOrders extends BaseFragment {
    private FragmentOrderBinding binding;
    private CompletedOrdersAdapter adapter;
    private HomeActivity activity;
    private FragmentCompletedOrdersMvvm mvvm;

    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;
    private String caterer_id = "28";

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        activityHomeGeneralMvvm = ViewModelProviders.of(this).get(ActivityHomeGeneralMvvm.class);

        activityHomeGeneralMvvm.getOnStatusSuccess().observe(activity, status -> {
            if (status.equals("completed")) {
                mvvm.getCompletedOrders(caterer_id);

            }
        });
        adapter = new CompletedOrdersAdapter(activity, this);

        mvvm = ViewModelProviders.of(this).get(FragmentCompletedOrdersMvvm.class);

        mvvm.getIsDataLoading().observe(activity, isLoading -> binding.swipeRefresh.setRefreshing(isLoading));

        mvvm.getOnDataSuccess().observe(activity, orderList -> {
            if (orderList.size() > 0) {
                if (adapter != null) {
                    adapter.updateList(orderList);
                    binding.tvNoData.setVisibility(View.GONE);
                }
            } else {
                binding.tvNoData.setVisibility(View.VISIBLE);
            }
        });

        binding.swipeRefresh.setOnRefreshListener(() -> {
            mvvm.getCompletedOrders(caterer_id);
        });
        binding.recyclerOrder.setAdapter(adapter);
        binding.recyclerOrder.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
        mvvm.getCompletedOrders(caterer_id);

    }


}