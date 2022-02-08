package com.etbakhly_provider.uis.activities_fragments_home.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.NewOrdersAdapter;
import com.etbakhly_provider.databinding.DialogAlertBinding;
import com.etbakhly_provider.databinding.FragmentOrderBinding;
import com.etbakhly_provider.model.OrderModel;
import com.etbakhly_provider.mvvm.ActivityHomeGeneralMvvm;
import com.etbakhly_provider.mvvm.FragmentNewOrdersMvvm;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.uis.activities_fragments_home.HomeActivity;
import com.etbakhly_provider.uis.activity_base.BaseFragment;

import java.util.ArrayList;

public class FragmentNewOrders extends BaseFragment {
    private FragmentOrderBinding binding;
    private NewOrdersAdapter adapter;
    private HomeActivity activity;
    private FragmentNewOrdersMvvm mvvm;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;
    private String caterer_id = "28";
    private String reason = "";

    public static FragmentNewOrders newInstance() {
        FragmentNewOrders fragment = new FragmentNewOrders();
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


        adapter = new NewOrdersAdapter(activity, this);
        mvvm = ViewModelProviders.of(this).get(FragmentNewOrdersMvvm.class);
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
                adapter.updateList(new ArrayList<>());
                binding.tvNoData.setVisibility(View.VISIBLE);
            }
        });

        mvvm.getOnOrderStatusSuccess().observe(activity, status -> {
            if (status == 1) {
                activityHomeGeneralMvvm.getOnStatusSuccess().setValue("approval");
                mvvm.getNewOrders(caterer_id);
            } else if (status == 2) {
                mvvm.getNewOrders(caterer_id);
            }
        });


        binding.swipeRefresh.setOnRefreshListener(() -> {
            mvvm.getNewOrders(caterer_id);
        });


        binding.recyclerOrder.setAdapter(adapter);
        binding.recyclerOrder.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));

        mvvm.getNewOrders(caterer_id);

    }


    public void changeStatus(OrderModel orderModel, String status) {
        if (status.equals("approval")) {
            mvvm.changeStatusOrder(status, orderModel.getId(), null);
        } else {
            createReasonDialog(status, orderModel.getId());
        }


    }

    private void createReasonDialog(String status, String order_id) {
        final AlertDialog dialog = new AlertDialog.Builder(activity)
                .create();

        DialogAlertBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_alert, null, false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();


        binding.radio1.setOnClickListener(view -> {
            reason = "";
            binding.anotherReason.setVisibility(View.GONE);
            binding.anotherReason.setText(null);
            binding.anotherReason.setError(null);

        });
        binding.radio2.setOnClickListener(view -> {
            reason = "";
            binding.anotherReason.setVisibility(View.GONE);
            binding.anotherReason.setText(null);
            binding.anotherReason.setError(null);


        });

        binding.radio3.setOnClickListener(view -> {
            reason = "";
            binding.anotherReason.setVisibility(View.VISIBLE);
        });
        binding.btnDone.setOnClickListener(view -> {
            if (binding.radio3.isChecked()) {
                reason = binding.anotherReason.getText().toString();
                if (!reason.isEmpty()) {
                    binding.anotherReason.setError(null);
                    Common.CloseKeyBoard(activity, binding.anotherReason);
                    mvvm.changeStatusOrder(status, order_id, reason);

                } else {
                    binding.anotherReason.setError(getString(R.string.field_required));
                }
            } else {
                mvvm.changeStatusOrder(status, order_id, reason);

            }
            dialog.dismiss();

        });
    }


}