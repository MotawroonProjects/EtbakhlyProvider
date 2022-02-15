package com.etbakhly_provider.uis.activity_dishes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.BuffetDishesAdapter;
import com.etbakhly_provider.adapter.CategoryDishesAdapter;
import com.etbakhly_provider.databinding.ActivityDishesBinding;
import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.model.DishModel;
import com.etbakhly_provider.mvvm.ActivityDishesMvvm;
import com.etbakhly_provider.uis.activity_add_dishes.AddDishesActivity;
import com.etbakhly_provider.uis.activity_base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class DishesActivity extends BaseActivity {
    ActivityDishesBinding binding;
    private CategoryDishesAdapter adapter;
    private BuffetDishesAdapter dishesAdapter;
    private ActivityDishesMvvm mvvm;
    private String kitchen_id = "27";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dishes);
        initView();
    }

    private void initView() {
        mvvm = ViewModelProviders.of(this).get(ActivityDishesMvvm.class);

        mvvm.getIsDataLoading().observe(this, isLoading -> {
            if (isLoading) {
                binding.progBar.setVisibility(View.VISIBLE);
            } else {
                binding.progBar.setVisibility(View.GONE);

            }
        });
        mvvm.onDataSuccess().observe(this, categories -> {
            if (categories.size() > 0) {
                updateUi();
                binding.tvNoData.setVisibility(View.GONE);

            } else {
                adapter.updateList(new ArrayList<>());
                binding.tvNoData.setVisibility(View.VISIBLE);

            }

        });
        mvvm.onDishSuccess().observe(this, dishesList -> {
            if (dishesList.size() > 0) {
                dishesAdapter.updateList(dishesList);
                binding.tvNoData.setVisibility(View.GONE);
            } else {
                dishesAdapter.updateList(new ArrayList<>());
                binding.tvNoData.setVisibility(View.VISIBLE);
            }
        });


        binding.setLang(getLang());
        adapter = new CategoryDishesAdapter(this);
        dishesAdapter = new BuffetDishesAdapter(this);
        mvvm.getDishes(kitchen_id);

        binding.recViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recViewCategory.setAdapter(adapter);

        binding.recViewDishes.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewDishes.setAdapter(dishesAdapter);
        binding.llBack.setOnClickListener(view -> finish());
        binding.llAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DishesActivity.this, AddDishesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateUi() {
        BuffetModel.Category category = mvvm.onDataSuccess().getValue().get(0);
        category.setSelected(true);

        mvvm.onDataSuccess().getValue().set(0, category);
        adapter.updateList(mvvm.onDataSuccess().getValue());
        updateDishes(category);
    }

    private void updateDishes(BuffetModel.Category category) {


        if (category.getDishes_buffet().size() > 0) {
            binding.tvNoData.setVisibility(View.GONE);
        } else {

            binding.tvNoData.setVisibility(View.VISIBLE);
        }
        dishesAdapter.updateList(category.getDishes_buffet());


    }

    public void setItemCategory(BuffetModel.Category category, int currentPos) {
        mvvm.setSelectedCategoryPos(currentPos);
        updateDishes(category);
    }
}