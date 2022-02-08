package com.etbakhly_provider.uis.activity_dishes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.BuffetDishesAdapter;
import com.etbakhly_provider.adapter.CategoryDishesAdapter;
import com.etbakhly_provider.databinding.ActivityDishesBinding;
import com.etbakhly_provider.uis.activity_base.BaseActivity;

public class DishesActivity extends BaseActivity {
    ActivityDishesBinding binding;
    private CategoryDishesAdapter adapter;
    private BuffetDishesAdapter dishesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dishes);
        initView();
    }

    private void initView() {
        binding.setLang(getLang());
        adapter = new CategoryDishesAdapter(this);
        dishesAdapter = new BuffetDishesAdapter(this);

        binding.recViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recViewCategory.setAdapter(adapter);

        binding.recViewDishes.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewDishes.setAdapter(dishesAdapter);
        binding.btnBack.setOnClickListener(view -> finish());
    }
}