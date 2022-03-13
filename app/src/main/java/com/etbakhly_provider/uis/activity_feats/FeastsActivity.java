package com.etbakhly_provider.uis.activity_feats;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.BuffetAdapter;
import com.etbakhly_provider.databinding.ActivityFeastsBinding;
import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.mvvm.ActivityFeastsMvvm;
import com.etbakhly_provider.uis.activity_add_feast.AddFeastActivity;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_feasts_details.FeastsDetailsActivity;

import java.util.ArrayList;

public class FeastsActivity extends BaseActivity {
    private ActivityFeastsBinding binding;
    private BuffetAdapter adapter;
    private ActivityFeastsMvvm mvvm;
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

        mvvm.onDataSuccess().observe(this, feastsList -> {
            if (feastsList.size() > 0) {
                binding.tvNoData.setVisibility(View.GONE);

                if (adapter != null) {
                    adapter.updateList(feastsList);
                }
            } else {
                adapter.updateList(new ArrayList<>());
                binding.tvNoData.setVisibility(View.VISIBLE);

            }
        });

        mvvm.getOnStatusSuccess().observe(this, status -> {
            mvvm.getFeasts(getUserModel().getData().getCaterer().getId(), this);
        });

        binding.setLang(getLang());
        adapter = new BuffetAdapter(this);
        binding.recViewFeasts.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewFeasts.setAdapter(adapter);

        mvvm.getFeasts(getUserModel().getData().getCaterer().getId(), this);
        binding.swipeRefresh.setOnRefreshListener(() -> mvvm.getFeasts(getUserModel().getData().getCaterer().getId(), this));

        binding.tvNoData.setVisibility(View.GONE);
        binding.llBack.setOnClickListener(view -> {
            finish();
        });
        binding.addFeast.setOnClickListener(view -> {
            req = 1;
            Intent intent = new Intent(FeastsActivity.this, AddFeastActivity.class);
            launcher.launch(intent);
        });
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == RESULT_OK) {
                mvvm.getFeasts(getUserModel().getData().getCaterer().getId(), this);

            }
        });
    }

    public void setItemData(BuffetModel feastsModel, int adapterPosition) {
        req = 1;
        mvvm.getSelectedPos().setValue(adapterPosition);

        Intent intent = new Intent(this, FeastsDetailsActivity.class);
        intent.putExtra("data", feastsModel);
        launcher.launch(intent);

    }

    public void deleteFeast(BuffetModel buffetModel) {
        mvvm.deleteFeasts(buffetModel.getId());
    }


    public void editFeast(BuffetModel feastsModel, int adapterPosition) {
        req = 1;

        Intent intent = new Intent(this, AddFeastActivity.class);
        intent.putExtra("data", feastsModel);
        launcher.launch(intent);
    }
}