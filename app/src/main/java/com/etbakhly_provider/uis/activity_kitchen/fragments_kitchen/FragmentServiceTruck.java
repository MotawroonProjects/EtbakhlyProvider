package com.etbakhly_provider.uis.activity_kitchen.fragments_kitchen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.FragmentServiceTruckBinding;
import com.etbakhly_provider.model.KitchenModel;
import com.etbakhly_provider.uis.activity_base.BaseFragment;
import com.etbakhly_provider.uis.activity_buffet.BuffetActivity;
import com.etbakhly_provider.uis.activity_dishes.DishesActivity;
import com.etbakhly_provider.uis.activity_kitchen.KitchenDetailsActivity;

public class FragmentServiceTruck extends BaseFragment {
    private FragmentServiceTruckBinding binding;
    private KitchenDetailsActivity activity;
    private KitchenModel model;

    public static FragmentServiceTruck newInstance(KitchenModel model) {
        FragmentServiceTruck fragment = new FragmentServiceTruck();
        Bundle args = new Bundle();
        args.putSerializable("data", model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (KitchenDetailsActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            model = (KitchenModel) getArguments().getSerializable("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_service_truck, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        binding.cardViewBuffet.setOnClickListener(v -> navigateToBuffetActivity());
        binding.cardViewDishes.setOnClickListener(v -> navigateToDishesActivity());

    }


    private void navigateToBuffetActivity() {
        Intent intent = new Intent(activity, BuffetActivity.class);
        startActivity(intent);
    }


    private void navigateToDishesActivity() {
        Intent intent = new Intent(activity, DishesActivity.class);
        startActivity(intent);
    }
}