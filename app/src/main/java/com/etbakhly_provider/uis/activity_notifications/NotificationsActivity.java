package com.etbakhly_provider.uis.activity_notifications;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.BuffetAdapter;
import com.etbakhly_provider.adapter.NotificationAdapter;
import com.etbakhly_provider.databinding.ActivityBuffetBinding;
import com.etbakhly_provider.databinding.ActivityNotificationsBinding;
import com.etbakhly_provider.mvvm.ActivityBuffetsMvvm;
import com.etbakhly_provider.mvvm.ActivityNotificationsMvvm;
import com.etbakhly_provider.uis.activity_add_buffet.AddBuffetActivity;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_buffet.BuffetActivity;

import java.util.ArrayList;

public class NotificationsActivity extends BaseActivity {
    private ActivityNotificationsBinding binding;
    private NotificationAdapter adapter;
    private ActivityNotificationsMvvm mvvm;
    private String option_id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notifications);
        initView();

    }

    private void initView() {
        setUpToolbar(binding.toolBar, getString(R.string.notifications), R.color.colorPrimary, R.color.white);
        mvvm = ViewModelProviders.of(this).get(ActivityNotificationsMvvm.class);
        binding.recViewLayout.tvNoData.setText(R.string.no_not_to_show);
        mvvm.getIsDataLoading().observe(this, isLoading -> {
            binding.recViewLayout.swipeRefresh.setRefreshing(isLoading);
        });
        mvvm.onDataSuccess().observe(this, list -> {

            if (list.size() > 0) {
                binding.recViewLayout.tvNoData.setVisibility(View.GONE);

                if (adapter != null) {
                    adapter.updateList(list);
                }
            } else {
                adapter.updateList(new ArrayList<>());
                binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);

            }
        });


        adapter = new NotificationAdapter(this);
        binding.recViewLayout.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewLayout.recView.setAdapter(adapter);
        if (getUserModel().getData().getType().equals("service")) {
            option_id = "1";
        } else if (getUserModel().getData().getType().equals("chef")) {
            option_id = "2";

        } else {
            option_id = "3";

        }

        mvvm.getNotifications(getUserModel().getData().getId(), option_id);

        binding.recViewLayout.swipeRefresh.setOnRefreshListener(() -> mvvm.getNotifications(getUserModel().getData().getId(), option_id));


    }
}