package com.etbakhly_provider.uis.activity_order_details;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.ActivityOrderDetailsBinding;
import com.etbakhly_provider.model.OrderModel;
import com.etbakhly_provider.mvvm.ActivityOrderDetailsMvvm;
import com.etbakhly_provider.uis.activity_base.BaseActivity;

public class OrderDetailsActivity extends BaseActivity {
    private ActivityOrderDetailsBinding binding;
    private ActivityOrderDetailsMvvm mvvm;
    private String order_id = "";
    private OrderModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");
    }

    private void initView() {
        setUpToolbar(binding.toolbar, getString(R.string.order_details), R.color.colorPrimary, R.color.white);
        mvvm = ViewModelProviders.of(this).get(ActivityOrderDetailsMvvm.class);
        binding.setModel(null);

        mvvm.getIsOrderDataLoading().observe(this, isLoading -> {
            if (isLoading) {
                binding.flLoader.setVisibility(View.VISIBLE);
            } else {
                binding.flLoader.setVisibility(View.GONE);

            }
        });
        mvvm.getOnOrderDetailsSuccess().observe(this, orderModel -> {
            this.model = orderModel;
            binding.setModel(model);

        });
        mvvm.getOrderDetails(order_id);

    }


}