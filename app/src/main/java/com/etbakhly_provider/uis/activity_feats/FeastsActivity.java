package com.etbakhly_provider.uis.activity_feats;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.BuffetAdapter;
import com.etbakhly_provider.databinding.ActivityFeastsBinding;
import com.etbakhly_provider.mvvm.ActivityFeastsMvvm;
import com.etbakhly_provider.uis.activity_base.BaseActivity;

public class FeastsActivity extends BaseActivity {
    private ActivityFeastsBinding binding;
    private BuffetAdapter adapter;
    private ActivityFeastsMvvm mvvm;
    private String kitchen_id = "27";
    private int req;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feasts);
        initView();
    }

    private void initView() {

        mvvm = ViewModelProviders.of(this).get(ActivityFeastsMvvm.class);
        mvvm.getIsDataLoading().observe(this, isLoading -> {
            binding.swipeRefresh.setRefreshing(isLoading);
        });
        mvvm.onDataSuccess().observe(this, buffetsList -> {
            if (buffetsList.size() > 0) {
                binding.tvNoData.setVisibility(View.GONE);

                if (adapter != null) {
                    adapter.updateList(buffetsList);
                }
            } else {
                binding.tvNoData.setVisibility(View.VISIBLE);

            }
        });

        binding.setLang(getLang());
        adapter = new BuffetAdapter(this);
        binding.recViewFeasts.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewFeasts.setAdapter(adapter);

        mvvm.getFeasts(kitchen_id, this);
        binding.swipeRefresh.setOnRefreshListener(() -> mvvm.getFeasts(kitchen_id, this));

        binding.tvNoData.setVisibility(View.GONE);
        binding.btnBack.setOnClickListener(view -> {
            finish();
        });
    }
}