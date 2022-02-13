package com.etbakhly_provider.uis.activity_kitchen.fragments_kitchen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etbakhly_provider.R;

import com.etbakhly_provider.databinding.ChooseDialogBinding;
import com.etbakhly_provider.databinding.FragmentServicesBinding;
import com.etbakhly_provider.model.KitchenModel;
import com.etbakhly_provider.uis.activity_feats.FeastsActivity;
import com.etbakhly_provider.uis.activity_base.BaseFragment;
import com.etbakhly_provider.uis.activity_buffet.BuffetActivity;
import com.etbakhly_provider.uis.activity_dishes.DishesActivity;
import com.etbakhly_provider.uis.activity_kitchen.KitchenDetailsActivity;


public class FragmentServices extends BaseFragment {
    private KitchenDetailsActivity activity;
    private FragmentServicesBinding binding;

    public static FragmentServices newInstance(KitchenModel model) {
        FragmentServices fragment = new FragmentServices();
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


            ChooseDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.choose_dialog, null, false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setView(binding.getRoot());
            dialog.show();
            binding.setLang(getLang());
            binding.BuffetOrBanquet.setText(getString(R.string.buffets_menu));
            binding.llBuffet.setOnClickListener(view1 -> {
                Intent intent = new Intent(activity, BuffetActivity.class);
                startActivity(intent);
                dialog.dismiss();
            });
            binding.llDishes.setOnClickListener(view12 -> {
                Intent intent = new Intent(activity, DishesActivity.class);
                startActivity(intent);
                dialog.dismiss();
            });
        });

        binding.cardViewBuffet.setOnClickListener(view -> createChooseDialog("buffet"));
        binding.cardViewBanquet.setOnClickListener(view -> createChooseDialog("banquet"));


    }
    private void createChooseDialog(String type) {
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        ChooseDialogBinding chooseDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.choose_dialog, null, false);
        chooseDialogBinding.setLang(getLang());
        chooseDialogBinding.setType(type);

        chooseDialogBinding.llBuffet.setOnClickListener(v -> {
            if (type.equals("buffet")) {
                navigateToBuffetActivity();
            } else {
                navigateToFeastsActivity();
            }
            dialog.cancel();

        });

        chooseDialogBinding.llDishes.setOnClickListener(v -> {

            navigateToDishesActivity();

            dialog.cancel();
        });

        dialog.setView(chooseDialogBinding.getRoot());
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_style_bg);
        dialog.show();
        activity.updateBlur(20f);

        dialog.setOnCancelListener(dialog1 -> activity.updateBlur(0));

    }

    private void navigateToBuffetActivity() {
        Intent intent = new Intent(activity, BuffetActivity.class);
//        intent.putExtra("kitchen_id", model.getId());
        startActivity(intent);
    }
    private void navigateToFeastsActivity() {
        Intent intent = new Intent(activity, FeastsActivity.class);
//        intent.putExtra("kitchen_id", model.getId());
        startActivity(intent);
    }

    private void navigateToDishesActivity() {
        Intent intent = new Intent(activity, DishesActivity.class);
//        intent.putExtra("kitchen_id", model.getId());
        startActivity(intent);
    }




}