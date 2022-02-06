package com.etbakhly_provider.uis.activities_fragments_home.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.PendingOrdersAdapter;
import com.etbakhly_provider.databinding.FragmentOrderBinding;
import com.etbakhly_provider.mvvm.ActivityHomeGeneralMvvm;
import com.etbakhly_provider.mvvm.FragmentPendingOrdersMvvm;
import com.etbakhly_provider.uis.activities_fragments_home.HomeActivity;


public class FragmentPendingOrders extends Fragment {
    private FragmentOrderBinding binding;
    private PendingOrdersAdapter adapter;
    private HomeActivity activity;
    private FragmentPendingOrdersMvvm mvvm;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;
    private String caterer_id = "28";

    public static FragmentPendingOrders newInstance() {
        FragmentPendingOrders fragment = new FragmentPendingOrders();
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
        mvvm = ViewModelProviders.of(this).get(FragmentPendingOrdersMvvm.class);
        activityHomeGeneralMvvm = ViewModelProviders.of(activity).get(ActivityHomeGeneralMvvm.class);
        mvvm.getOnStatusSuccess().observe(activity, status -> {
            if (status == 1) {
                mvvm.getPendingOrder(caterer_id);
            } else if (status == 2) {
                mvvm.getPendingOrder(caterer_id);
                activityHomeGeneralMvvm.getOnStatusSuccess().setValue("completed");
            }
        });
        adapter = new PendingOrdersAdapter(activity, this);

        mvvm.getIsDataLoading().observe(activity, isLoading -> {
            binding.swipeRefresh.setRefreshing(isLoading);
        });

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
        mvvm.getOnDataSuccess().observe(activity, orderModelList -> {
            if (orderModelList.size() > 0) {
                if (adapter != null) {
                    adapter.updateList(orderModelList);
                    binding.tvNoData.setVisibility(View.GONE);
                }
            } else {
                adapter.updateList(orderModelList);
                binding.tvNoData.setVisibility(View.VISIBLE);
            }
        });

        binding.swipeRefresh.setOnRefreshListener(() -> {
            mvvm.getPendingOrder(caterer_id);
        });
        binding.recyclerOrder.setAdapter(adapter);
        binding.recyclerOrder.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
        mvvm.getPendingOrder(caterer_id);

    }


    public void changeStatus(String id, String status) {
        mvvm.changeStatusOrder(status, id);
    }
}