package com.etbakhly_provider.uis.activity_country;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.CountryAdapter;
import com.etbakhly_provider.databinding.ActivityCountryBinding;
import com.etbakhly_provider.model.CountryModel;
import com.etbakhly_provider.mvvm.ActivityCountryMvvm;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_city.CityActivity;


public class CountryActivity extends BaseActivity {
    private ActivityCountryBinding binding;
    private String lang = "";
    private ActivityCountryMvvm mvvm;
    private CountryAdapter adapter;
    private int req;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_country);
        initView();
    }

    private void initView() {
        setUpToolbar(binding.toolBar, getString(R.string.choose_country), R.color.white, R.color.black);
        binding.recViewLayout.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        lang = getLang();
        mvvm = ViewModelProviders.of(this).get(ActivityCountryMvvm.class);
        mvvm.getIsLoading().observe(this, isLoading -> {
            binding.recViewLayout.swipeRefresh.setRefreshing(isLoading);
            if (isLoading) {
                binding.recViewLayout.tvNoData.setVisibility(View.GONE);
            }
        });

        mvvm.getCountryLiveData().observe(this, countriesList -> {
            if (adapter != null) {
                adapter.updateList(countriesList);
            }
        });

        adapter = new CountryAdapter(this, getLang());
        binding.recViewLayout.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewLayout.recView.setAdapter(adapter);
        binding.recViewLayout.recView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.recViewLayout.swipeRefresh.setOnRefreshListener(() -> mvvm.getCountries());


        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == RESULT_OK) {
                CountryModel cityModel = (CountryModel) result.getData().getSerializableExtra("city");
                Intent intent = getIntent();
                intent.putExtra("country", mvvm.getSelectedCountryLiveData().getValue());
                intent.putExtra("city", cityModel);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        mvvm.getCountries();
    }


    public void setItemCountry(CountryModel countryModel) {
        req = 1;
        mvvm.setSelectedCountryLiveData(countryModel);
        Intent intent = new Intent(this, CityActivity.class);
        intent.putExtra("data", countryModel.getId());
        launcher.launch(intent);
    }
}