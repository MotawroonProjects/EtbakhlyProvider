package com.etbakhly_provider.uis.activity_language;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.ActivityLanguageBinding;
import com.etbakhly_provider.uis.activity_base.BaseActivity;


public class LanguageActivity extends BaseActivity {
    private ActivityLanguageBinding binding;
    private String lang = "";
    private String selectedLang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_language);
        initView();
    }

    private void initView() {
        lang = getLang();
        selectedLang = lang;
        if (lang.equals("ar")) {
            binding.tvArabic.setTextColor(getResources().getColor(R.color.color9));
            binding.tvEnglish.setTextColor(getResources().getColor(R.color.gray35));

            binding.chArabic.setVisibility(View.VISIBLE);
            binding.chEnglish.setVisibility(View.GONE);
            binding.chArabic.setChecked(true);
            binding.chEnglish.setChecked(false);

        } else {
            binding.tvEnglish.setTextColor(getResources().getColor(R.color.color9));
            binding.tvArabic.setTextColor(getResources().getColor(R.color.gray35));

            binding.chEnglish.setVisibility(View.VISIBLE);
            binding.chArabic.setVisibility(View.GONE);
            binding.chEnglish.setChecked(true);
            binding.chArabic.setChecked(false);

        }

        binding.llArabic.setOnClickListener(view -> {
            selectedLang = "ar";

            if (!selectedLang.equals(lang)) {
                binding.btnDone.setVisibility(View.VISIBLE);

            } else {
                binding.btnDone.setVisibility(View.INVISIBLE);

            }
            binding.tvArabic.setTextColor(getResources().getColor(R.color.color9));
            binding.tvEnglish.setTextColor(getResources().getColor(R.color.gray35));

            binding.chArabic.setVisibility(View.VISIBLE);
            binding.chEnglish.setVisibility(View.GONE);
            binding.chArabic.setChecked(true);
            binding.chEnglish.setChecked(false);

        });

        binding.llEnglish.setOnClickListener(view -> {
            selectedLang = "en";
            if (!selectedLang.equals(lang)) {
                binding.btnDone.setVisibility(View.VISIBLE);

            } else {
                binding.btnDone.setVisibility(View.INVISIBLE);

            }
            binding.tvEnglish.setTextColor(getResources().getColor(R.color.color9));
            binding.tvArabic.setTextColor(getResources().getColor(R.color.gray35));

            binding.chEnglish.setVisibility(View.VISIBLE);
            binding.chArabic.setVisibility(View.GONE);
            binding.chEnglish.setChecked(true);
            binding.chArabic.setChecked(false);
        });

        binding.btnDone.setOnClickListener(view -> {

            Intent intent = getIntent();
            intent.putExtra("lang", selectedLang);
            setResult(RESULT_OK, intent);
            finish();
        });
    }


}