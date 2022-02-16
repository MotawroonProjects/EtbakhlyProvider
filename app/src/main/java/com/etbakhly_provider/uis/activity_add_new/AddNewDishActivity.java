package com.etbakhly_provider.uis.activity_add_new;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.AddNewDishAdapter;
import com.etbakhly_provider.databinding.ActivityAddNewDishBinding;
import com.etbakhly_provider.uis.activity_base.BaseActivity;

public class AddNewDishActivity extends BaseActivity {
    private ActivityAddNewDishBinding binding;
    private AddNewDishAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_add_new_dish);
        initView();
    }

    private void initView() {
        binding.setLang(getLang());

        adapter=new AddNewDishAdapter(this);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(adapter);

        binding.llBack.setOnClickListener(view -> finish());
    }
}