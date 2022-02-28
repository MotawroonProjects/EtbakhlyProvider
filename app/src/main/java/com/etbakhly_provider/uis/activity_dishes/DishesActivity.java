package com.etbakhly_provider.uis.activity_dishes;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.AddCategoryDishesAdapter;
import com.etbakhly_provider.adapter.BuffetDishesAdapter;
import com.etbakhly_provider.databinding.ActivityDishesBinding;
import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.model.DishModel;
import com.etbakhly_provider.mvvm.ActivityDishesMvvm;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.uis.activity_add_dishes.AddDishesActivity;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DishesActivity extends BaseActivity {
    private ActivityDishesBinding binding;
    private AddCategoryDishesAdapter adapter;
    private BuffetDishesAdapter dishesAdapter;
    private ActivityDishesMvvm mvvm;
    private ActivityResultLauncher<Intent> launcher;
    private int req;
    private BottomSheetBehavior behavior;
    private BuffetModel.Category selectedCategory = null;
    private int selectedCategoryPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dishes);
        initView();
    }

    private void initView() {
        mvvm = ViewModelProviders.of(this).get(ActivityDishesMvvm.class);
        adapter = new AddCategoryDishesAdapter(this);
        dishesAdapter = new BuffetDishesAdapter(this);

        mvvm.getIsDataLoading().observe(this, isLoading -> {
            if (isLoading) {

                binding.tvNoData.setVisibility(View.GONE);
                if (adapter!=null){
                    if (mvvm.onDataSuccess().getValue()!=null){
                        mvvm.onDataSuccess().getValue().clear();
                        adapter.notifyDataSetChanged();
                    }

                }

                binding.progBar.setVisibility(View.VISIBLE);
            } else {
                binding.progBar.setVisibility(View.GONE);

            }
        });
        mvvm.onDataSuccess().observe(this, categories -> {
            if (categories.size() > 1) {
                if (behavior.getState()==BottomSheetBehavior.STATE_EXPANDED){
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

                binding.tvNoData.setVisibility(View.GONE);

                updateUi();
                adapter.updateList(mvvm.onDataSuccess().getValue());

            } else {
                adapter.updateList(mvvm.onDataSuccess().getValue());
                binding.tvNoData.setVisibility(View.VISIBLE);

            }

        });
        mvvm.onDishSuccess().observe(this, dishesList -> {
            if (dishesList.size() > 0) {

                binding.tvNoData.setVisibility(View.GONE);

                if (dishesAdapter != null) {
                    dishesAdapter.updateList(dishesList);
                }
            } else {
                dishesAdapter.updateList(new ArrayList<>());
                binding.tvNoData.setVisibility(View.VISIBLE);
            }
        });

        mvvm.onEditSuccess().observe(this, category -> {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            if (adapter != null) {
                mvvm.updateCategory(category,selectedCategoryPos);
                adapter.updateItem(category, selectedCategoryPos);

            }
            selectedCategory = null;
            selectedCategoryPos = -1;

        });
        mvvm.getOnDeleteSuccess().observe(this, aBoolean -> {
            if (aBoolean) {
                if (adapter != null) {
                    adapter.deleteItem(selectedCategoryPos);
                    mvvm.getDishes(getUserModel().getData().getCaterer().getId());
                }
                selectedCategoryPos = -1;
            }
        });
        mvvm.getOnDeleteDishSuccess().observe(this, aBoolean -> {
            if (aBoolean) {
                mvvm.getDishes(getUserModel().getData().getCaterer().getId());

            }

        });

        behavior = BottomSheetBehavior.from(binding.sheet.root);
        binding.setLang(getLang());


        mvvm.getDishes(getUserModel().getData().getCaterer().getId());


        binding.recViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recViewCategory.setAdapter(adapter);

        binding.recViewDishes.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewDishes.setAdapter(dishesAdapter);
        binding.llBack.setOnClickListener(view -> finish());
        binding.fab.setOnClickListener(view -> {
            navigateToAddDishActivity();
        });

        binding.sheet.llClose.setOnClickListener(view -> {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });

        binding.sheet.btnAdd.setOnClickListener(view -> {
            String category_name = binding.sheet.edtName.getText().toString();
            if (!category_name.isEmpty()) {
                binding.sheet.edtName.setError(null);
                Common.CloseKeyBoard(this, binding.sheet.edtName);
                if (selectedCategory == null) {
                    mvvm.addCategory(category_name, getUserModel().getData().getCaterer().getId(), this);

                } else {

                    mvvm.editCategory(selectedCategory,category_name,selectedCategory.getId(), this, selectedCategoryPos);
                }

            } else {
                binding.sheet.edtName.setError(getString(R.string.field_required));
            }
        });
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == RESULT_OK && result.getData() != null) {
                DishModel dishModel = (DishModel) result.getData().getSerializableExtra("data");
                if (mvvm.getSelectedDishPos().getValue()!=1){
                    mvvm.onDishSuccess().getValue().set(mvvm.getSelectedDishPos().getValue(), dishModel);
                    adapter.notifyItemChanged(mvvm.getSelectedDishPos().getValue());
                    mvvm.getSelectedDishPos().setValue(-1);
                }
            }
        });
    }

    public void openSheet(BuffetModel.Category category, int currentPos) {
        selectedCategory = category;
        selectedCategoryPos = currentPos;

        if (category != null) {
            binding.sheet.edtName.setText(category.getTitel());
            binding.sheet.btnAdd.setText(R.string.update);
            binding.sheet.tvTitle.setText(R.string.update_cat);

        } else {
            binding.sheet.edtName.setText(null);
            binding.sheet.btnAdd.setText(R.string.add);
            binding.sheet.tvTitle.setText(R.string.add_category);
        }
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

    }

    private void navigateToAddDishActivity() {
        req = 1;
        List<BuffetModel.Category> categoryList = new ArrayList<>(mvvm.onDataSuccess().getValue());
        categoryList.remove(0);

        Intent intent = new Intent(DishesActivity.this, AddDishesActivity.class);
        intent.putExtra("data", (Serializable) categoryList);
        launcher.launch(intent);
    }

    private void updateUi() {

        binding.fab.setVisibility(View.VISIBLE);
        BuffetModel.Category category = mvvm.onDataSuccess().getValue().get(1);
        category.setSelected(true);

        mvvm.onDataSuccess().getValue().set(1, category);
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

    public void deleteItemCategory(BuffetModel.Category category, int currentPos) {
        selectedCategoryPos = currentPos;
        mvvm.deleteCategory(category.getId(), this, currentPos);


    }

    public void navigateToAddCategory() {
        openSheet(null, -1);
    }


    public void deleteDish(DishModel dishModel) {
        mvvm.deleteDish(dishModel.getId());
    }


    public void editDish(DishModel dishModel, int adapterPosition) {
        req=1;
        mvvm.getSelectedDishPos().setValue(adapterPosition);

        List<BuffetModel.Category> categoryList = new ArrayList<>(mvvm.onDataSuccess().getValue());
        categoryList.remove(0);

        Intent intent = new Intent(this, AddDishesActivity.class);
        intent.putExtra("data2", dishModel);
        intent.putExtra("data", (Serializable) categoryList);
        launcher.launch(intent);
    }
}