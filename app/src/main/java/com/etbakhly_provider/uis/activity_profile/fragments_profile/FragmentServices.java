package com.etbakhly_provider.uis.activity_profile.fragments_profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.DialogAlertBinding;
import com.etbakhly_provider.databinding.DialogLayoutBinding;
import com.etbakhly_provider.databinding.FragmentServicesBinding;
import com.etbakhly_provider.uis.activity_base.BaseFragment;
import com.etbakhly_provider.uis.activity_buffet.BuffetActivity;
import com.etbakhly_provider.uis.activity_dishes.DishesActivity;
import com.etbakhly_provider.uis.activity_profile.ProfileActivity;


public class FragmentServices extends BaseFragment {
    private ProfileActivity activity;
    private FragmentServicesBinding binding;

    public static FragmentServices newInstance() {
        FragmentServices fragment = new FragmentServices();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (ProfileActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_services, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        binding.cardViewBuffet.setOnClickListener(view -> {
            final AlertDialog dialog = new AlertDialog.Builder(activity)
                    .create();

            DialogLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_layout, null, false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setView(binding.getRoot());
            dialog.show();
            binding.llMenu.setOnClickListener(view1 -> {
                Intent intent = new Intent(activity, BuffetActivity.class);
                startActivity(intent);
                dialog.dismiss();
            });
            binding.llChooseDishes.setOnClickListener(view12 -> {
                Intent intent = new Intent(activity, DishesActivity.class);
                startActivity(intent);
                dialog.dismiss();
            });
        });
        binding.cardViewBanquet.setOnClickListener(view -> {
//            final AlertDialog dialog=new AlertDialog.Builder(activity)
//                    .create();
//
//            DialogLayoutBinding binding=DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.dialog_layout,null,false);
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.setView(binding.getRoot());
//            dialog.show();
//            binding.llMenu.setOnClickListener(view1 -> {
//                Intent intent=new Intent(activity, BuffetActivity.class);
//                startActivity(intent);
//                dialog.dismiss();
//            });
//            binding.llChooseDishes.setOnClickListener(view12 -> {
//                Intent intent=new Intent(activity, DishesActivity.class);
//                startActivity(intent);
//                dialog.dismiss();
//            });
        });
    }
}