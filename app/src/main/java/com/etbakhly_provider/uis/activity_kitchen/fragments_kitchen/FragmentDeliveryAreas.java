package com.etbakhly_provider.uis.activity_kitchen.fragments_kitchen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.DeliveryDetailsAdapter;
import com.etbakhly_provider.databinding.FragmentDeliveryAreasBinding;
import com.etbakhly_provider.model.KitchenModel;

import com.etbakhly_provider.model.ZoneCover;
import com.etbakhly_provider.uis.activity_base.BaseFragment;
import com.etbakhly_provider.uis.activity_kitchen.KitchenDetailsActivity;

import java.util.List;


public class FragmentDeliveryAreas extends BaseFragment {
    private KitchenDetailsActivity activity;
    private FragmentDeliveryAreasBinding binding;
    private DeliveryDetailsAdapter adapter;
    private KitchenModel model;

    public static FragmentDeliveryAreas newInstance(KitchenModel model) {
        FragmentDeliveryAreas fragment = new FragmentDeliveryAreas();
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
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_delivery_areas, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        if (model.getZone_cover().size() > 0) {
            adapter = new DeliveryDetailsAdapter(activity);
            adapter.updateList(model.getZone_cover());
            binding.recView.setLayoutManager(new GridLayoutManager(activity, 2));
            binding.recView.setAdapter(adapter);
            binding.tvNoData.setVisibility(View.GONE);
        } else {
            binding.tvNoData.setVisibility(View.VISIBLE);
        }

    }


}