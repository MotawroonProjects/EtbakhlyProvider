package com.etbakhly_provider.uis.activities_home.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.etbakhly_provider.adapter.PendingOrdersAdapter;
import com.etbakhly_provider.databinding.FragmentOrderBinding;
import com.etbakhly_provider.model.OrderModel;
import com.etbakhly_provider.mvvm.ActivityHomeGeneralMvvm;
import com.etbakhly_provider.mvvm.FragmentPendingOrdersMvvm;
import com.etbakhly_provider.uis.activities_home.HomeActivity;
import com.etbakhly_provider.uis.activity_base.BaseFragment;
import com.etbakhly_provider.uis.activity_order_details.OrderDetailsActivity;


public class FragmentPendingOrders extends BaseFragment {
    private FragmentOrderBinding binding;
    private PendingOrdersAdapter adapter;
    private HomeActivity activity;
    private FragmentPendingOrdersMvvm mvvm;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;
    private int req;
    private ActivityResultLauncher<Intent> launcher;

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
                mvvm.getPendingOrder(getUserModel().getData().getCaterer().getId());
            } else if (status == 2) {
                mvvm.getPendingOrder(getUserModel().getData().getCaterer().getId());
                activityHomeGeneralMvvm.getOnStatusSuccess().setValue("completed");
            }
            activityHomeGeneralMvvm.getOnFragmentCompleteOrderRefreshed().setValue(true);

        });

        activityHomeGeneralMvvm.getOnFragmentPendingOrderRefreshed().observe(activity,isRefreshed->{
            if (isRefreshed){
                mvvm.getPendingOrder(getUserModel().getData().getCaterer().getId());

            }
        });
        adapter = new PendingOrdersAdapter(activity, this);

        mvvm.getIsDataLoading().observe(activity, isLoading -> {
            binding.swipeRefresh.setRefreshing(isLoading);
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
            mvvm.getPendingOrder(getUserModel().getData().getCaterer().getId());
        });
        binding.recyclerOrder.setAdapter(adapter);
        binding.recyclerOrder.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
        mvvm.getPendingOrder(getUserModel().getData().getCaterer().getId());

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req==1&&result.getResultCode()== Activity.RESULT_OK){
                mvvm.getPendingOrder(getUserModel().getData().getCaterer().getId());
                activityHomeGeneralMvvm.getOnFragmentCompleteOrderRefreshed().setValue(true);
            }
        });
    }


    public void changeStatus(String id, String status) {
        mvvm.changeStatusOrder(status, id);
    }

    public void navigateToDetails(OrderModel orderModel) {
        req =1;
        Intent intent=new Intent(activity, OrderDetailsActivity.class);
        intent.putExtra("order_id",orderModel.getId());
        launcher.launch(intent);
    }


}