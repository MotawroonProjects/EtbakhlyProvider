package com.etbakhly_provider.uis.activity_buffet;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.BuffetAdapter;
import com.etbakhly_provider.databinding.ActivityBuffetBinding;
import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.model.DishModel;
import com.etbakhly_provider.mvvm.ActivityBuffetsMvvm;
import com.etbakhly_provider.uis.activity_add_buffet.AddBuffetActivity;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_buffet_details.BuffetDetailsActivity;
import com.etbakhly_provider.uis.activity_kitchen.KitchenDetailsActivity;

import java.util.ArrayList;

public class BuffetActivity extends BaseActivity {
    private ActivityBuffetBinding binding;
    private BuffetAdapter adapter;
    private ActivityBuffetsMvvm mvvm;
//    private String kitchen_id = "27";
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
                adapter.updateList(new ArrayList<>());
                binding.tvNoData.setVisibility(View.VISIBLE);

            }
        });
        mvvm.getOnStatusSuccess().observe(this, status -> {

            mvvm.getBuffets(getUserModel().getData().getCaterer().getId(),this);
        });

        binding.setLang(getLang());
        adapter = new BuffetAdapter(this);
        binding.recViewBuffet.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewBuffet.setAdapter(adapter);

        mvvm.getBuffets(getUserModel().getData().getCaterer().getId(), this);

        binding.swipeRefresh.setOnRefreshListener(() -> mvvm.getBuffets(getUserModel().getData().getCaterer().getId(), this));

        binding.tvNoData.setVisibility(View.GONE);
        binding.llBack.setOnClickListener(view -> {
            finish();
        });
        binding.addBuffet.setOnClickListener(view -> {
            Intent intent = new Intent(BuffetActivity.this, AddBuffetActivity.class);

            startActivity(intent);

        });
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == RESULT_OK && result.getData() != null) {
                BuffetModel buffetModel = (BuffetModel) result.getData().getSerializableExtra("data");
                if (mvvm.getSelectedPos().getValue() != -1) {
                    mvvm.onDataSuccess().getValue().set(mvvm.getSelectedPos().getValue(), buffetModel);
                    adapter.notifyItemChanged(mvvm.getSelectedPos().getValue());
                    mvvm.getSelectedPos().setValue(-1);
                }

            }
        });
    }
    public void setItemData(BuffetModel buffetModel, int adapterPosition) {
        req = 1;
        mvvm.getSelectedPos().setValue(adapterPosition);

        Intent intent = new Intent(this, BuffetDetailsActivity.class);
        intent.putExtra("data", buffetModel);
        launcher.launch(intent);
    }

    public void deleteBuffet(BuffetModel buffetModel) {
        mvvm.deleteBuffet(buffetModel.getId());
    }
}