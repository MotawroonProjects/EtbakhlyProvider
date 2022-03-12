package com.etbakhly_provider.uis.activity_offers;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.BuffetAdapter;
import com.etbakhly_provider.adapter.OfferAdapter;
import com.etbakhly_provider.databinding.ActivityFeastsBinding;
import com.etbakhly_provider.databinding.ActivityOffersBinding;
import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.model.OfferModel;
import com.etbakhly_provider.mvvm.ActivityFeastsMvvm;
import com.etbakhly_provider.mvvm.ActivityOffersMvvm;
import com.etbakhly_provider.uis.activity_add_feast.AddFeastActivity;
import com.etbakhly_provider.uis.activity_add_offer.AddOfferActivity;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_feasts_details.FeastsDetailsActivity;
import com.etbakhly_provider.uis.activity_feats.FeastsActivity;

import java.util.ArrayList;

public class OffersActivity extends BaseActivity {
    private ActivityOffersBinding binding;
    private OfferAdapter adapter;
    private ActivityOffersMvvm mvvm;
    private int req;
    private ActivityResultLauncher<Intent> launcher;
    private int selectedPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_offers);
        initView();
    }

    private void initView() {
        setUpToolbar(binding.toolbar, getString(R.string.offers), R.color.colorPrimary, R.color.white);

        mvvm = ViewModelProviders.of(this).get(ActivityOffersMvvm.class);
        mvvm.getIsDataLoading().observe(this, isLoading -> {
            binding.recViewLayout.swipeRefresh.setRefreshing(isLoading);
        });

        mvvm.onDataSuccess().observe(this, offerList -> {
            if (offerList.size() > 0) {
                binding.recViewLayout.tvNoData.setVisibility(View.GONE);

                if (adapter != null) {
                    adapter.updateList(offerList);
                }
            } else {
                binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);

            }
        });

        mvvm.getOnDeleteSuccess().observe(this, pos -> {
            if (mvvm.onDataSuccess().getValue() != null && mvvm.onDataSuccess().getValue().size() == 0) {
                binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);
            }
            if (adapter != null) {
                adapter.notifyItemRemoved(pos);
            }
        });
        binding.recViewLayout.tvNoData.setText(R.string.no_offers);
        adapter = new OfferAdapter(this);
        binding.recViewLayout.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewLayout.recView.setAdapter(adapter);

        mvvm.getOffers(getUserModel().getData().getCaterer().getId());
        binding.recViewLayout.swipeRefresh.setOnRefreshListener(() -> mvvm.getOffers(getUserModel().getData().getCaterer().getId()));

        binding.addOffer.setOnClickListener(v -> {
            navigateToAddOfferActivity(null);
        });
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == RESULT_OK && result.getData() != null) {
                String action = result.getData().getStringExtra("action");
                OfferModel offerModel = (OfferModel) result.getData().getSerializableExtra("data");
                if (action.equals("add")) {
                    if (mvvm.onDataSuccess().getValue() != null) {
                        mvvm.onDataSuccess().getValue().add(0, offerModel);
                        adapter.notifyItemInserted(0);
                    }
                } else {
                    mvvm.onDataSuccess().getValue().set(selectedPos, offerModel);
                    adapter.notifyItemChanged(selectedPos);
                }

                binding.recViewLayout.tvNoData.setVisibility(View.GONE);
            }
        });

    }

    private void navigateToAddOfferActivity(OfferModel model) {
        req = 1;
        Intent intent = new Intent(this, AddOfferActivity.class);
        intent.putExtra("data", model);
        launcher.launch(intent);
    }


    public void edit(OfferModel offerModel, int adapterPosition) {
        selectedPos = adapterPosition;
        navigateToAddOfferActivity(offerModel);
    }

    public void delete(OfferModel offerModel, int adapterPosition) {
        mvvm.deleteOffers(offerModel.getId(), adapterPosition);
    }
}