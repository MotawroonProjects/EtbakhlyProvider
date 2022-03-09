package com.etbakhly_provider.uis.activity_buffet_details;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.BuffetMenuAdapter;
import com.etbakhly_provider.databinding.ActivityBuffetDetailsBinding;
import com.etbakhly_provider.databinding.AddCategoryDialogFragment2Binding;
import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.model.DishModel;
import com.etbakhly_provider.mvvm.ActivityBuffetDetailsMvvm;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.uis.activity_add_buffet.add_buffet_dish_activity.AddBuffetDishActivity;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class BuffetDetailsActivity extends BaseActivity {
    private ActivityBuffetDetailsBinding binding;
    private BuffetMenuAdapter adapter;
    private BuffetModel model;
    private ActivityBuffetDetailsMvvm mvvm;
    private ActivityResultLauncher<Intent> launcher;
    private int req;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_buffet_details);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        model = (BuffetModel) intent.getSerializableExtra("data");
    }

    private void initView() {
        mvvm = ViewModelProviders.of(this).get(ActivityBuffetDetailsMvvm.class);
        mvvm.getIsDataLoading().observe(this, isLoading -> {
            if (isLoading) {
                binding.llData.setVisibility(View.GONE);
                binding.progBar.setVisibility(View.VISIBLE);

            } else {
                binding.progBar.setVisibility(View.GONE);

            }
        });

        mvvm.onCategoryDataSuccess().observe(this, categories -> {
            binding.llData.setVisibility(View.VISIBLE);
            if (adapter != null) {
                adapter.updateList(categories);


            }
        });
        mvvm.getCategoryAddedSuccess().observe(this, category -> {
            if (adapter != null && mvvm.onCategoryDataSuccess().getValue() != null) {
                adapter.notifyItemChanged(mvvm.onCategoryDataSuccess().getValue().size() - 1);
            }
        });


        mvvm.getCategoryDishes(getUserModel().getData().getCaterer().getId(), model.getId(),this);

        mvvm.getOnCategoryDeletedSuccess().observe(this, pos -> {
            if (adapter != null) {
                adapter.notifyItemRemoved(pos);
            }
        });

        mvvm.getOnDishUpdatedSuccess().observe(this, mainPos -> {
            if (adapter != null) {
                adapter.notifyItemChanged(mainPos);
            }
        });

        binding.setLang(getLang());
        binding.setModel(model);
        adapter = new BuffetMenuAdapter(this);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(adapter);
        binding.llBack.setOnClickListener(v -> {
            finish();
        });
        binding.llAddCategory.setOnClickListener(v -> {
            showCategoryDialog();
        });

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == RESULT_OK && result.getData() != null) {
                DishModel dishModel = (DishModel) result.getData().getSerializableExtra("data");
                String action = result.getData().getStringExtra("action");
                if (action!=null){
                    if(action.equals("add")){
                        if (mvvm.onCategoryDataSuccess().getValue() != null && mvvm.getOnItemSelected().getValue() != null && adapter != null) {
                            List<DishModel> list = mvvm.onCategoryDataSuccess().getValue().get(mvvm.getOnItemSelected().getValue()).getDishes_buffet();
                            list.add(dishModel);
                            mvvm.getOnDishUpdatedSuccess().setValue(mvvm.getOnItemSelected().getValue());

                        }
                    }else {
                        if (mvvm.onCategoryDataSuccess().getValue() != null && mvvm.getOnItemSelected().getValue() != null && mvvm.getOnChildItemSelected().getValue() != null && adapter != null) {
                            List<DishModel> list = mvvm.onCategoryDataSuccess().getValue().get(mvvm.getOnItemSelected().getValue()).getDishes_buffet();
                            list.set(mvvm.getOnChildItemSelected().getValue(),dishModel);
                            mvvm.getOnDishUpdatedSuccess().setValue(mvvm.getOnItemSelected().getValue());

                        }
                    }

                }


            }
        });
    }

    private void showCategoryDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        AddCategoryDialogFragment2Binding fragmentBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.add_category_dialog_fragment2, null, false);
        dialog.setContentView(fragmentBinding.getRoot());
        fragmentBinding.btnAdd.setOnClickListener(v -> {
            String name = fragmentBinding.edtName.getText().toString();
            if (!name.isEmpty()) {
                fragmentBinding.edtName.setError(null);
                Common.CloseKeyBoard(this, fragmentBinding.edtName);
                mvvm.addCategory(name, getUserModel().getData().getCaterer().getId(),model.getId(),this);
                dialog.dismiss();

            } else {
                fragmentBinding.edtName.setError(getString(R.string.field_required));

            }

        });

        fragmentBinding.llClose.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }


    public void deleteCategory(BuffetModel.Category category, int adapterPosition) {
        mvvm.deleteCategory(category.getId(), this, adapterPosition);
    }

    public void navigateToAddNewBuffetDish(BuffetModel.Category category, int adapterPosition) {
        mvvm.getOnItemSelected().setValue(adapterPosition);
        req = 1;
        Intent intent = new Intent(BuffetDetailsActivity.this, AddBuffetDishActivity.class);
        intent.putExtra("data3", model.getId());
        intent.putExtra("data", category.getId());
        launcher.launch(intent);
    }


    public void navigateToUpdateDish(DishModel dishModel, int mainCategoryPos, int adapterPosition) {
        mvvm.getOnItemSelected().setValue(mainCategoryPos);
        mvvm.getOnChildItemSelected().setValue(adapterPosition);
        req = 1;
        Intent intent = new Intent(BuffetDetailsActivity.this, AddBuffetDishActivity.class);
        intent.putExtra("data", dishModel.getCategory_dishes_id());
        intent.putExtra("data3", model.getId());
        intent.putExtra("data2", dishModel);
        launcher.launch(intent);

    }

    public void deleteDish(DishModel dishModel, int mainCategoryPos, int childPos) {
        mvvm.deleteDish(dishModel.getId(), this, mainCategoryPos, childPos);
    }
}