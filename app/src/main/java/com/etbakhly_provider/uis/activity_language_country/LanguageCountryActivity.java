package com.etbakhly_provider.uis.activity_language_country;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.ActivityLanguageCountryBinding;
import com.etbakhly_provider.model.CountryModel;
import com.etbakhly_provider.model.SelectedLocation;
import com.etbakhly_provider.model.UserSettingsModel;
import com.etbakhly_provider.mvvm.ActivityCountryLanguageMvvm;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_country.CountryActivity;
import com.etbakhly_provider.uis.activity_map.MapActivity;

public class LanguageCountryActivity extends BaseActivity {
    private ActivityLanguageCountryBinding binding;
    private String lang = "ar";
    private ActivityCountryLanguageMvvm mvvm;
    private ActivityResultLauncher<Intent> launcher;
    private int req;
    private String selectedLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_language_country);
        initView();
    }

    private void initView() {
        lang = getLang();
        selectedLang = lang;
        mvvm = ViewModelProviders.of(this).get(ActivityCountryLanguageMvvm.class);


        binding.llAr.setOnClickListener(v -> {
            if (!selectedLang.equals("ar")) {
                selectedLang = "ar";
                binding.tvAr.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                binding.imageArChecked.setVisibility(View.VISIBLE);

                binding.tvEn.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.imageEnChecked.setVisibility(View.INVISIBLE);
            }


        });


        binding.llEn.setOnClickListener(v -> {
            if (!selectedLang.equals("en")) {
                selectedLang = "en";
                binding.tvEn.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                binding.imageEnChecked.setVisibility(View.VISIBLE);

                binding.tvAr.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.imageArChecked.setVisibility(View.INVISIBLE);


            }


        });

        binding.llCountry.setOnClickListener(v -> {
            req = 1;
            Intent intent = new Intent(this, CountryActivity.class);
            launcher.launch(intent);

        });

        binding.btnSave.setOnClickListener(v -> {
            Intent intent = getIntent();
            intent.putExtra("lang", selectedLang);
            setResult(RESULT_OK, intent);
            finish();

        });
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == RESULT_OK && result.getData() != null) {
                CountryModel countryModel = (CountryModel) result.getData().getSerializableExtra("country");
                CountryModel cityModel = (CountryModel) result.getData().getSerializableExtra("city");


                mvvm.setSelectedCountry(countryModel);
                mvvm.setSelectedCity(cityModel);
                binding.setCity(mvvm.getSelectedCountry().getValue().getTitel() + " - " + mvvm.getSelectedCity().getValue().getTitel());

                navigateToMapActivity();
            } else if (req == 2 && result.getResultCode() == RESULT_OK && result.getData() != null) {
                SelectedLocation location = (SelectedLocation) result.getData().getSerializableExtra("location");
                mvvm.setSelectedLocation(location);
            }
        });
    }

    private void navigateToMapActivity() {
        req = 2;
        Intent intent = new Intent(this, MapActivity.class);
        launcher.launch(intent);
    }

}