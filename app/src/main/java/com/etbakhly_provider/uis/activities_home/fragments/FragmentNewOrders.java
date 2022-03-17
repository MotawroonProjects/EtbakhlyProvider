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
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
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
import com.etbakhly_provider.uis.activities_home.HomeActivity;
import com.etbakhly_provider.uis.activity_base.BaseFragment;
import com.etbakhly_provider.uis.activity_order_details.OrderDetailsActivity;

import java.util.ArrayList;

public class FragmentNewOrders extends BaseFragment {
    private FragmentOrderBinding binding;
    private NewOrdersAdapter adapter;
    private HomeActivity activity;
    private FragmentNewOrdersMvvm mvvm;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;
    private String reason = "";
    private int req;
    private ActivityResultLauncher<Intent> launcher;

    public static FragmentNewOrders newInstance() {
        FragmentNewOrders fragment = new FragmentNewOrders();
        return fragment;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                String order_status = result.getData().getStringExtra("order_status");
                if (order_status.equals("approval")) {
                    mvvm.getNewOrders(getUserModel().getData().getCaterer().getId());
                    activityHomeGeneralMvvm.getOnFragmentPendingOrderRefreshed().setValue(true);
                    activityHomeGeneralMvvm.getPosChangedSuccess().setValue(1);

                } else if (order_status.equals("refusal")) {
                    mvvm.getNewOrders(getUserModel().getData().getCaterer().getId());

                }
            }
        });
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

        activityHomeGeneralMvvm = ViewModelProviders.of(activity).get(ActivityHomeGeneralMvvm.class);


        mvvm = ViewModelProviders.of(this).get(FragmentNewOrdersMvvm.class);
        mvvm.getIsDataLoading().observe(activity, isLoading -> {
            binding.swipeRefresh.setRefreshing(isLoading);
        });

        activityHomeGeneralMvvm.getOnFragmentNewOrderRefreshed().observe(activity, isRefreshed -> {
            if (isRefreshed) {
                mvvm.getNewOrders(getUserModel().getData().getCaterer().getId());

            }

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
                activityHomeGeneralMvvm.getOnFragmentPendingOrderRefreshed().setValue(true);
                activityHomeGeneralMvvm.getPosChangedSuccess().setValue(1);
                mvvm.getNewOrders(getUserModel().getData().getCaterer().getId());
            } else if (status == 2) {
                mvvm.getNewOrders(getUserModel().getData().getCaterer().getId());
            }

        });


        binding.swipeRefresh.setOnRefreshListener(() -> {
            mvvm.getNewOrders(getUserModel().getData().getCaterer().getId());
        });

        adapter = new NewOrdersAdapter(activity, this);
        binding.recyclerOrder.setAdapter(adapter);
        binding.recyclerOrder.setLayoutManager(new LinearLayoutManager(activity));

        mvvm.getNewOrders(getUserModel().getData().getCaterer().getId());


    }


    public void changeStatus(OrderModel orderModel, String status) {
        if (status.equals("approval")) {
            mvvm.changeStatusOrder(status, orderModel.getId(), null, activity);
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
            Log.e("dd","ff");
        });
        binding.btnDone.setOnClickListener(view -> {
            if (binding.radio3.isChecked()) {
                reason = binding.anotherReason.getText().toString();
                if (!reason.isEmpty()) {
                    dialog.dismiss();
                    binding.anotherReason.setError(null);
                    Common.CloseKeyBoard(activity, binding.anotherReason);
                    mvvm.changeStatusOrder(status, order_id, reason, activity);

                } else {
                    binding.anotherReason.setError(getString(R.string.field_required));
                }
            } else {
                dialog.dismiss();

                mvvm.changeStatusOrder(status, order_id, reason, activity);

            }

        });
    }


    public void navigateToDetails(OrderModel orderModel) {
        req = 1;
        Intent intent = new Intent(activity, OrderDetailsActivity.class);
        intent.putExtra("order_id", orderModel.getId());
        launcher.launch(intent);
    }
}