package com.etbakhly_provider.uis.activity_buffet;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.BuffetAdapter;
import com.etbakhly_provider.databinding.ActivityBuffetBinding;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_kitchen.KitchenDetailsActivity;

public class BuffetActivity extends BaseActivity {
    private ActivityBuffetBinding binding;
    BuffetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_buffet);
        initView();

    }

    private void initView() {
        binding.setLang(getLang());
        adapter = new BuffetAdapter(this);
        binding.recViewBuffet.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.recViewBuffet.setAdapter(adapter);
        binding.tvNoData.setVisibility(View.GONE);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuffetActivity.this, KitchenDetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}