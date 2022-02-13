package com.etbakhly_provider.uis.activity_buffet;

import androidx.activity.result.ActivityResultLauncher;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.BuffetAdapter;
import com.etbakhly_provider.databinding.ActivityBuffetBinding;
import com.etbakhly_provider.mvvm.ActivityBuffetsMvvm;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_kitchen.KitchenDetailsActivity;

public class BuffetActivity extends BaseActivity {
    private ActivityBuffetBinding binding;
    private BuffetAdapter adapter;
    private ActivityBuffetsMvvm mvvm;
    private String kitchen_id = "";
    private int req;

    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_buffet);
        initView();

    }

    private void initView() {
        mvvm = ViewModelProviders.of(this).get(ActivityBuffetsMvvm.class);
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
        binding.recViewBuffet.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewBuffet.setAdapter(adapter);
        binding.tvNoData.setVisibility(View.GONE);
        binding.btnBack.setOnClickListener(view -> {
            finish();
        });
    }
}