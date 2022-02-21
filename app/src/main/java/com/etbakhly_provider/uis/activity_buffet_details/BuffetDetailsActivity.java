package com.etbakhly_provider.uis.activity_buffet_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.BuffetMenuAdapter;
import com.etbakhly_provider.databinding.ActivityBuffetDetailsBinding;
import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.uis.activity_add_dishes.AddDishesActivity;
import com.etbakhly_provider.uis.activity_base.BaseActivity;

public class BuffetDetailsActivity extends BaseActivity {
    private ActivityBuffetDetailsBinding binding;
    private BuffetMenuAdapter adapter;
    private BuffetModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_buffet_details);
        getDataFromIntent();
        initView();
    }
    private void getDataFromIntent() {
        Intent intent = getIntent();
        model = (BuffetModel) intent.getSerializableExtra("data");
    }

    private void initView() {
        binding.setLang(getLang());
        binding.setModel(model);
        adapter = new BuffetMenuAdapter(this);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter.updateList(model.getCategor_dishes());
        binding.recView.setAdapter(adapter);
      //  binding.recView.setNestedScrollingEnabled(false);

        binding.llBack.setOnClickListener(v -> {
            finish();
        });
    }

    public void navigateToAddNewBuffetDish() {
        Intent intent=new Intent(BuffetDetailsActivity.this, AddDishesActivity.class);
        startActivity(intent);
    }
}